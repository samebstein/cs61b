public class Body{
	public static final double G = 6.67e-11;
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Body(double xP, double yP, double xV, 
		double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;

	}

	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b){
		double dx = this.xxPos - b.xxPos;
		double dy = this.yyPos - b.yyPos;
		return java.lang.Math.sqrt(java.lang.Math.pow(dx, 2) + 
			java.lang.Math.pow(dy, 2));
	}
		
/** 
Determine total force on object exerted by input object
F = (G *m1*m2) / rsquared
*/
	public double calcForceExertedBy(Body b){
		double rsquared = java.lang.Math.pow(calcDistance(b), 2);
		double force = (G * this.mass * b.mass) / rsquared;
		return force;


	}

	/** 
	Determine individual forces in x 
	and y direction on object exerted by input object
	Fx = (F*dx/r)
	Fy = (F*dy/r)
	*/
	public double calcForceExertedByX(Body b){
		double F = calcForceExertedBy(b);
		double dx = b.xxPos - this.xxPos;
		double forcex = (F * dx) / calcDistance(b);
		return forcex;
	}
	public double calcForceExertedByY(Body b){
		double F = calcForceExertedBy(b);
		double dy = b.yyPos - this.yyPos;
		double forcey = (F * dy) / calcDistance(b);
		return forcey;
	}

	public double calcNetForceExertedByX(Body[] bodys){
		double sum = 0;
		for(int i = 0; i < bodys.length; i += 1){
			if (this.mass == bodys[i].mass){
				continue;
			}
			sum = sum + calcForceExertedByX(bodys[i]);
		}
		return sum;
	}
		
	
	public double calcNetForceExertedByY(Body[] bodys){
		double sum = 0;
		for(int i = 0; i < bodys.length; i += 1){
			if (this.mass == bodys[i].mass){
				continue;
			}
			sum = sum + calcForceExertedByY(bodys[i]);
		}
		return sum;
	}
	/** Updates position and velocity of object given time of 
	force exerted in x and y direction as 
	well as forces exerted in x and y*/
	public void update(double dt, double fX, double fY){
		double aX = fX / this.mass;
		double aY = fY / this.mass;

		double vnewx = this.xxVel + dt*aX;
		double vnewy = this.yyVel + dt*aY;
		this.xxVel = vnewx;
		this.yyVel = vnewy;

		double pnewx = this.xxPos + dt*this.xxVel;
		double pnewy = this.yyPos + dt*this.yyVel;
		this.xxPos = pnewx;
		this.yyPos = pnewy;
	}







	}