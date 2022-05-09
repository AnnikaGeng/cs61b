public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		double xDis = Math.abs(p.xxPos - this.xxPos);
		double yDis = Math.abs(p.yyPos - this.yyPos);
		double dis = Math.sqrt(xDis * xDis + yDis * yDis);
		return dis;
	}

	public double calcForceExertedBy(Planet p) {
		double res = (G * this.mass * p.mass);
		double dis = calcDistance(p);
		res = res / (dis * dis);
		return res;
	}

	public double calcForceExertedByX(Planet p) {
		double res = calcForceExertedBy(p) * (p.xxPos - this.xxPos);
		double dis = calcDistance(p);
		return res / dis;
	}

	public double calcForceExertedByY(Planet p) {
		double res = calcForceExertedBy(p) * (p.yyPos - this.yyPos);
		double dis = calcDistance(p);
		return res / dis;
	}

	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double xNetForce = 0;
		for (int i=0; i<allPlanets.length; i++) {
			if (allPlanets[i].equals(this)) {
				continue;
			} else {
				xNetForce += calcForceExertedByX(allPlanets[i]);
			}
		}
		return xNetForce;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double yNetForce = 0;
		for (int i=0; i<allPlanets.length; i++) {
			if (allPlanets[i].equals(this)) {
				continue;
			} else {
				yNetForce += calcForceExertedByY(allPlanets[i]);
			}
		}
		return yNetForce;
	}

	public void update(double dt, double fX, double fY) {
		/** calculate the net acceleration */
		double xxa = fX / this.mass;
		double xxy = fY / this.mass;
		/** calculate the velocity */
		xxVel = this.xxVel + dt * xxa;
		yyVel = this.yyVel + dt * xxy;
		/** calculate the position */
		xxPos = this.xxPos + dt * xxVel;
		yyPos = this.yyPos + dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
	}

}