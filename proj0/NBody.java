public class NBody{

	public static double readRadius(String a){
		In in = new In(a);
		int numberOfPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}
}