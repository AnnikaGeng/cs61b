public class NBody {
	@Deprecated
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int number = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int number = in.readInt();
		in.readDouble();
		Planet[] res = new Planet[number];
		for (int i = 0; i<number; i++) {
			double xp = in.readDouble();
			double yp = in.readDouble();
			double xv = in.readDouble();
			double yv = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			Planet p = new Planet(xp,yp,xv,yv,m,img);
			res[i] = p;
		}
		return res;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		int n = planets.length;

		String imagetoDraw = "images/starfield.jpg";

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius,radius);
		StdDraw.clear();
		StdDraw.picture(0,0,imagetoDraw);

		for(Planet p: planets) {
			p.draw();
		}
		StdDraw.show();

		for (double time=0; time <= T; time += dt) {
			double[] xForces = new double[n];
			double[] yForces = new double[n];
			for (int i=0; i<n; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}

			for (int i=0; i<n; i++) {
				planets[i].update(dt, xForces[i], yForces[i]);
			}

			StdDraw.picture(0,0,imagetoDraw);
			for(Planet p: planets) {
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
	                  		planets[i].yyVel, planets[i].mass, planets[i].imgFileName);}  
	}

}