public class hello{

	public static double readRadius(String a){
		In in = new In(a);
		int numberOfPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Body[] readBodies(String a){
		In in = new In(a);
		int numberOfPlanets = in.readInt();
		double radius = in.readDouble();

		Body[] arrayOfPlanets = new Body[numberOfPlanets];

		for (int i = 0; i < numberOfPlanets; i +=1){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			arrayOfPlanets[i] = new Body(xxPos, yyPos, 
				xxVel, yyVel, mass, imgFileName);
		}
		return arrayOfPlanets;
	}

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dT = Double.parseDouble(args[1]);
		String filename = args[2];
		Body[] bodys = readBodies(filename);
		double radius = readRadius(filename);
		double negativeRadius = -1 * radius;

		StdDraw.setXscale(negativeRadius, radius);
		StdDraw.setYscale(negativeRadius, radius);
		StdDraw.picture(0, 0, "./images/starfield.jpg");

		for (Body b: bodys){
			b.draw();
		}
	}
}



