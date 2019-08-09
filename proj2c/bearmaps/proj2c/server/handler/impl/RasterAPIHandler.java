package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import org.apache.commons.math3.analysis.function.Ulp;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bearmaps.proj2c.utils.Constants.SEMANTIC_STREET_GRAPH;
import static bearmaps.proj2c.utils.Constants.ROUTE_LIST;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        System.out.println("yo, wanna know the parameters given by the web browser? They are:");
        System.out.println(requestParams);

        /* Parameter variables from user request. */
        double lrlon = requestParams.get("lrlon");
        double ullon = requestParams.get("ullon");
        double w = requestParams.get("w");
        double h = requestParams.get("h");
        double ullat = requestParams.get("ullat");
        double lrlat = requestParams.get("lrlat");

        /* Result variables. */
        Map<String, Object> results = new HashMap<>();
        String[][] twoDString;
        int depth = findDepth(lrlon, ullon, w);
        boolean query_success = isQuerySuccessful(ullon, lrlon, lrlat, ullat);
        double raster_ul_lon;
        double raster_ul_lat;
        double raster_lr_lon;
        double raster_lr_lat;

        /* Squares per side and length of each square variables. */
        double squaresPerSide = Math.pow(2, depth);
        double squareLength = (Constants.ROOT_LRLON - Constants.ROOT_ULLON) / squaresPerSide;
        double squareLengthY = (Constants.ROOT_ULLAT - Constants.ROOT_LRLAT) / squaresPerSide;

        //Upper Left X and Y Value of query.
        int ULXValue = findX(ullon, squareLength, squaresPerSide);
        int ULYValue = findY(ullat, squareLengthY, squaresPerSide);
        String upperLeftBox = valueToFileName(depth, ULXValue, ULYValue);
        raster_ul_lon = xToLon(ULXValue, squareLength);
        raster_ul_lat = yToLat(ULYValue, squareLengthY);

        /* Lower Right X and Y Value of query.
        * The difference between the X values will tell us the number of
        * columns we need in our String[][] and the Y values the number of rows. */
        int LRXValue = findX(lrlon, squareLength, squaresPerSide);
        int LRYValue = findY(lrlat, squareLengthY, squaresPerSide);
        String lowerRightBox = valueToFileName(depth, LRXValue, LRYValue);
        raster_lr_lon = xToLon(LRXValue, squareLength);
        raster_lr_lat = yToLat(LRYValue, squareLengthY);

        /* Row and Column dimension variables.
        * And instantiation of twoDString variable.*/
        int rows = findRows(LRYValue, ULYValue);
        int columns = findColumns(LRXValue, ULXValue);
        String[] OneDString = makePngList(ULYValue, LRYValue, ULXValue, LRXValue, rows, columns, depth);
        twoDString = listToTwoD(OneDString, rows, columns);



        results.put("render_grid", twoDString);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);




        /**
         * Tested hardcoded version to see if image popped up.
         * String[][] hardCode = new String[1][2];
        hardCode[0] = new String[]{"d1_x0_y1.png", "d1_x1_y1.png", "d1_x1_y1.png"};
        results.put("render_grid", hardCode);
        results.put("raster_ul_lon", -122.2998046875);
        results.put("raster_ul_lat", 37.892195547244356);
        results.put("raster_lr_lon", -122.2119140625);
        results.put("raster_lr_lat", 37.82280243352756);
        results.put("depth", 0);
        results.put("query_success", true);
         */
        return results;
    }

    /**
     * Returns the Two dimensional string array of the files needed to compile the user query.
     * @param s String array.
     * @param rows Number.
     * @param columns Number.
     * @return String[][].
     */
    public String[][] listToTwoD(String[] s, int rows, int columns) {
        String[][] twoD = new String[rows][columns];
        int k = 0;
        for (int i = 0; i < rows; i += 1) {
            for (int j = 0; j < columns; j+= 1) {
                twoD[i][j] = s[k];
                k += 1;
            }
        }
        return twoD;
    }

    /**
     * Takes x, y coordinate values for both upper left coordinate and lower right coordinate
     * and returns a String[] containing all png files necessary to compile that image.
     * @param ULYValue
     * @param LRYValue
     * @param ULXValue
     * @param LRXValue
     * @param rows
     * @param columns
     * @param depth
     * @return String array.
     */
    public String[] makePngList(int ULYValue, int LRYValue, int ULXValue,
                                 int LRXValue, int rows, int columns, int depth) {
        String[] s = new String[rows * columns];
        int k = 0;
        for (int i = ULYValue; i < LRYValue + 1; i += 1) {
            for (int j = ULXValue; j < LRXValue + 1; j += 1) {
                s[k] = valueToFileName(depth, j, i);
                k += 1;
            }
        }
        return s;
    }

    /**
     * From the X coordinate values from the upper left coordinate and lower right coordinate,
     * findColumns method returns the number of rows for the twoDString array.
     * @param LRXValue Integer x value form the lower right coordinate.
     * @param ULXValue Integer x value from the upper left coordinate.
     * @return Integer value for number of columns necessary for twoDStringArray
     */
    private int findColumns(int LRXValue, int ULXValue) {
        return LRXValue - ULXValue + 1;
    }

    /** Method acts in the same manner as the findColumns method, except this method takes in
     * the corresponding Y values of the two coordinates and returns the number of necessary rows.
     * @param LRYValue Integer y value.
     * @param ULYValue Integer Y value.
     * @return Integer value for number rows necessary for twoDStringArray.
     */
    private int findRows(int LRYValue, int ULYValue) {
        return LRYValue - ULYValue + 1;
    }


    /**
     * Takes a value x (ie from the d4_x3_y3.png file and returns the x longitude
     * of either the top left or bottom right depending on parameter.
     * @param x Integer value.
     * @param squareLength Double value.
     * @return Double longitude value.
     */
    private double xToLon(int x , double squareLength) {
        double startLon = Constants.ROOT_ULLON;
        for (int i = 0; i < x; i += 1) {
            startLon += squareLength;
        }
        return startLon;
    }

    /** Takes a value y (ie from the d4_x3_y3.png file and returns the y latitude
     * of either the top left or bottom right coordinate depending on parameter. */
    private double yToLat(int y, double squareLength) {
        double startLat = Constants.ROOT_ULLAT;
        for (int i = 0; i < y; i += 1) {
            startLat -= squareLength;
        }
        return startLat;
    }

    /**
     * Returns a String file name that takes in a depth, x and y value and turns it into
     * the corresponding file name in our data.
     * Used initally for finding the corresponding upperleft box and lower right box of
     * user's query.
     * @param depth Integer Depth,
     * @param x Double x value.
     * @param y Double y value.
     * @return String file name.
     */
    public String valueToFileName(int depth, int x, int y) {
        return "d" + depth +"_x" + x + "_y" + y + ".png";
    }


    /**
     * Given upper left longitude, find the X value that corresponds to the correct
     * png image in data.
     * @param lon Double longitude of users two query points;
     * @param squareLength Distance per side of every square at certain depth.
     * @param squaresPerSide Number of squares per side.
     * @return Integer value for x value.
     */
    private int findX(double lon, double squareLength, double squaresPerSide) {
        double startX = Constants.ROOT_ULLON + squareLength;
        int xValue = 0;

        while (lon > startX && xValue < squaresPerSide - 1) {
            xValue += 1;
            startX += squareLength;
        }
        return xValue;
    }

    /**
     * Same idea as findX method; however, findY takes in a latitude coordinate
     * and returns the Y value of the box it would be placed in.
     * @param lat Double value of one of users two latitude values.
     * @param squareLength Distance per side of every square at certain depth.
     * @param squaresPerSide Number of squares per side.
     * @return Integer value for y value.
     */
    private int findY(double lat, double squareLength, double squaresPerSide) {
        double startY = Constants.ROOT_ULLAT - squareLength;
        int yValue = 0;

        while (lat < startY && yValue < squaresPerSide - 1) {
            yValue += 1;
            startY -= squareLength;
        }
        return yValue;
    }




    /**
     * Takes a user's query longitude and latitude values and determines whether a
     * successful rastering is possible.
     * @param ullon
     * @param lrlon
     * @param lrlat
     * @param ullat
     * @return Boolean value for whether the users request box has any points within the root box.
     */
    private boolean isQuerySuccessful(double ullon, double lrlon, double lrlat, double ullat) {
        return !(ullon > Constants.ROOT_LRLON) && !(lrlon < Constants.ROOT_ULLON)
                && !(lrlat > Constants.ROOT_ULLAT) && !(ullat < Constants.ROOT_LRLAT);
    }


    /**
     * Will be used as a private method for the public processRequest method
     * that takes in the following three parameters and finds the longitudinal distance
     * per pixel and returns the integer value of depth.
     * @param lrlon Lower right longitude of the image.
     * @param ullon Upper left longitude of the image.
     * @param w Width of the image (or box) in pixels that user requests.
     * @return Integer value that specify's the sharpness of images that will be
     *      combined together for the user.
     */
    public int findDepth(double lrlon, double ullon, double w) {
        double maxLonDPP = (lrlon - ullon) / w;
        int depth = 0;
        double startDDP = (Constants.ROOT_LRLON - Constants.ROOT_ULLON) / Constants.TILE_SIZE;

        while (startDDP > maxLonDPP && depth < 7) {
            depth += 1;
            startDDP /= 2;
        }

        return depth;
    }





























    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
