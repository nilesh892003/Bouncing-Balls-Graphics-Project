/**
 * <p>
 * This project is a 3D ball game that has a 3D enviroment and number of balls that are bounded in a box and handled by using a paddle.
 * </p>
 * <p>This project has the following features
 * <ul>
 * <li> It has the 3D environment.</li>
 * <li>It has the paddle to control the motion of the balls </li>
 * <li>The balls cannot leave the box </li>
 * <li>The paddle can be controlled by the Mouse and Keyboard</li>
 * 
 * </ul>
 * </p>
 * 
 * <p>
 * Keys to control the motion of the Paddle.
 * NOTE: Numpad is required to access the motion of the paddle by keyboard.
 * 1:UP(Numpad 8) : to move the paddle up.
 * 2:DOWN(Numpad 2) : to move the paddle down.
 * 3:LEFT (Numpad 4) : to move the paddle left.
 * 4:RIGHT (Numpad 6) : to move the paddle right.
 * 5:NUMPAD 7 : to move the paddle front in Z direction.
 * 6:NUMPAD 9: to move the paddle in back in Z direction.
 * 7: NUMPAD 1 & NUMPAD 3 : to Rotate the paddle Around Y axis.
 * 8: Key W & S : used to rotate the paddle around the X-Axis.
 * 9:Key A & D : Used to rotate the paddle around the Z-Axis.
 * </p>
 * 
 * 
 * * <p>
 * CS-652 : Project,Spring 2012 
 * project_nsingh6_Spring2012.java created on April 25 , 2012,6:00 PM
 * 
 * @author Nilesh Singh
 * @version 1.0
 *  </p>
 * 
 */

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.IOException;
import javax.media.opengl.*;
import java.io.*;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import javax.imageio.*;
import com.sun.opengl.util.GLUT;



public class Project_nsingh6_Spring2012 extends HW4_nsingh6_BoxRot implements KeyListener,
		MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	// Image import for texture mapping
	static byte[] img;
	static int imgW, imgH, imgType;

	// Image name
	static final int[] EARTH_TEX = new int[1];

	static float[] earthC = new float[3];

	static float[] mars = new float[3];

	static float[] venus = new float[3];

	static float[] jupitor = new float[3];

	static float[] saturn = new float[3];

	static int depth = 0;
	static int cnt = 1;

	static float a = 0;
	static float c = 0;
	static float b = 0.05f;
	static float d = 0.075f;
	static float e = 0;
	static float f = 0.1f;

	static float a1 = 0;
	static float c1 = 0;
	static float b1 = 0.1f;
	static float d1 = 0.075f;
	static float e1 = 0;
	static float f1 = 0.125f;

	static float a2 = 0;
	static float c2 = 0;
	static float b2 = 0.25f;
	static float d2 = 0.06f;
	static float e2 = 0;
	static float f2 = 0.1f;

	// for extra ball 4

	static float a3 = 0;
	static float c3 = 0;
	static float b3 = 0.15f;
	static float d3 = 0.085f;
	static float e3 = 0;
	static float f3 = 0.1f;

	// for extra ball 5

	static float a4 = 0.1f;
	static float c4 = 0;
	static float b4 = 0.15f;
	static float d4 = 0.085f;
	static float e4 = 0;
	static float f4 = 0.1f;

	// x direction increment/decrement
	int move_fact = 20;
	int mx = 0, my = 0, mz = 0;

	//Angle of rotation for the spheres
	static double rotAngle = 0;
	static double rotAngle1 = 0;
	static double rotAngle2 = 0;
	static double rotAngle3 = 0;
	static double rotAngle4 = 0;
	static double rotAngleglu = 0;
	static double rotAngleglu1 = 0;
	static double rotAngleglu2 = 0;

	// bounce count;
	int bounce = 0;
	
	double point[][] = new double[4][3];
	
	final GLUT glut = new GLUT();

	double sVdata[][] = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 }, { -1, 0, 0 },
			{ 0, -1, 0 }, { 0, 0, -1 } };

	// current matrix on the matrix stack
	static float[] currM = new float[16];

	static float tmpD = 0;

	// Different color and position declaration

	float white[] = { 1, 1, 1, 1 };
	float position[] = { 1, 1, 0, 0 };
	float whitish[] = { 0.8f, 0.8f, 0.8f, 1 };
	float origin[] = { earthC[0], earthC[1], earthC[2], 1 };
	float origin1[] = { mars[0], mars[1], mars[2], 1 };
	float origin2[] = { venus[0], venus[1], venus[2], 1 };
	float black[] = { 0, 0, 0, 1 };
	float red[] = { 1, 0, 0, 1 };
	float green[] = { 0, 1, 0, 1 };
	float blue[] = { 0, 0, 1, 1 };
	float cyan[] = { 0, 1, 1, 1 };
	float magenta[] = { 1, 0, 1, 1 };
	float yellow[] = { 1, 1, 0, 1 };

	float blackish[] = { 0.3f, 0.3f, 0.3f, 0.3f };
	float redish[] = { .3f, 0, 0, 1 };
	float greenish[] = { 0, .3f, 0, 1 };
	float blueish[] = { 0, 0, .3f, 1 };
	float spot_direction[] = { -1, 0, 0, 1 };

	// transparent colors
	float twhite[] = { 1, 1, 1, 0.3f };

	float tred[] = { 1, 0, 0, 0.4f };
	float tgreen[] = { 0, 1, 0, 0.5f };
	float tblue[] = { 0, 0, 1, 0.3f };

	
	int k = 0;
	double cntx, cnty, cntz;

	public Project_nsingh6_Spring2012() {

		canvas.addKeyListener(this);
		canvas.addMouseMotionListener(this);

		// center of the plane...
		cntx = (((point[0][0] + point[3][0]) / 2) - 10);
		cnty = ((point[0][1] + point[2][1]) / 2);
		cntz = ((point[0][2] + point[2][2]) / 2);
	}

	public void init(GLAutoDrawable glDrawable) {

		
		//Paddle Declaration.
		point[0][0] = -WIDTH / 6;
		point[0][1] = 0;
		point[0][2] = 30;

		point[1][0] = point[0][0] + 30;
		point[1][1] = point[0][1] + 60;
		point[1][2] = -30;

		point[2][0] = point[0][0] + 90;
		point[2][1] = point[1][1];
		point[2][2] = -30;

		point[3][0] = point[0][0] + 60;
		point[3][1] = point[0][1];
		point[3][2] = 30;

		super.init(glDrawable);

		
		// Texture initiation
		initTexture();

		
		
		gl.glEnable(GL.GL_LIGHTING);		//Enable the lights
		gl.glEnable(GL.GL_NORMALIZE);		//normalization
		

		// ball 1 earth
		gl.glEnable(GL.GL_LIGHT0);
		// gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, origin,0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, greenish, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, greenish, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, green, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_EMISSION, greenish, 0);

		// ball 2: mars
		gl.glEnable(GL.GL_LIGHT1);
		// gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, position,0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, redish, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, redish, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, red, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_EMISSION, red, 0);

		// ball 3:venus
		gl.glEnable(GL.GL_LIGHT2);
		// gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, position,0);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, blueish, 0);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, blueish, 0);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, blue, 0);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_EMISSION, blueish, 0);

		// global light source

		gl.glEnable(GL.GL_LIGHT3);

		gl.glLightfv(GL.GL_LIGHT3, GL.GL_POSITION, position, 0);
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_AMBIENT, blackish, 0);
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_DIFFUSE, whitish, 0);
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_SPECULAR, white, 0);

		
	}

	public void display(GLAutoDrawable drawable) {

		
		// gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);   //to create the wireframe of the environment
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);


		myMaterialColor(black, white, white, blackish);
		
		//Draw the paddle in our 3D environment.
		gl.glPushMatrix();

		gl.glRotated(rotAngleglu / 7, 0.0, 1.0, 0.0);
		gl.glRotated(rotAngleglu1 / 7, 0.0, 0.0, 1.0);
		gl.glRotated(rotAngleglu2 / 7, 1.0, 0.0, 0.0);

		gl.glBegin(GL.GL_POLYGON);

		gl.glNormal3d(point[0][0], point[0][1], point[0][2]);
		gl.glVertex3d(point[0][0], point[0][1], point[0][2]);

		gl.glNormal3d(point[1][0], point[1][1], point[1][2]);
		gl.glVertex3d(point[1][0], point[1][1], point[1][2]);

		gl.glNormal3d(point[2][0], point[2][1], point[2][2]);
		gl.glVertex3d(point[2][0], point[2][1], point[2][2]);

		gl.glNormal3d(point[3][0], point[3][1], point[3][2]);
		gl.glVertex3d(point[3][0], point[3][1], point[3][2]);

		
		gl.glEnd(); // Finished Drawing The Polygon

		gl.glPopMatrix();


		
		//Plane Equation of the paddle

		double A10, B10, C10, D10;

		A10 = point[0][1] * (point[1][2] - point[2][2]) + point[1][1]
				* (point[2][2] - point[0][2]) + point[2][1]
				* (point[0][2] - point[1][2]);
		B10 = point[0][2] * (point[1][0] - point[2][0]) + point[1][2]
				* (point[2][0] - point[0][0]) + point[2][2]
				* (point[0][0] - point[1][0]);
		C10 = point[0][0] * (point[1][1] - point[2][1]) + point[1][0]
				* (point[2][1] - point[0][1]) + point[2][0]
				* (point[0][1] - point[1][1]);
		D10 = point[0][0]
				* (point[1][1] * point[2][2] - point[2][1] * point[1][2])
				+ point[1][0]
				* (point[2][1] * point[0][2] - point[0][1] * point[2][2])
				+ point[2][0]
				* (point[0][1] * point[1][2] - point[1][1] * point[0][2]);

		double ctr[] = { cntx-30, cnty, cntz };

		if (Math.abs(distance(earthC, ctr)) < 300) {

		
		double dist = Math.abs(A10 * earthC[0] + B10 * earthC[1] + C10
					* earthC[2] - D10)
					/ Math.sqrt((A10 * A10) + (B10 * B10) + (C10 * C10));

		if (dist < 0.8) {
			
			if(A10*earthC[0]+B10*earthC[1]+C10*earthC[2]-D10 <=0){

				bounce++;
		
				b = -b;
				a += 5 * b;

				f = -f;
				e += 5 * f;

				d = -d;
				c += 7 * d;
			}
			}

		}
		
		
		if (distance(mars, ctr) < 300)

		{
			double dist1 = Math.abs(A10 * mars[0] + B10 * mars[1] + C10
					* mars[2] - D10)
					/ Math.sqrt((A10 * A10) + (B10 * B10) + (C10 * C10));
			if(A10*mars[0]+B10*mars[1]+C10*mars[2]-D10 <=0){

			if (dist1 < 0.8) {

				bounce++;

				k += 1;

				System.out.println("strike" + k);

				b1 = -b1;
				a1 += b1;

				f1 = -f1;
				e1 += f1;

				d1 = -d1;
				c1 += d1;
			}
			}
		}

		if (distance(venus, ctr) < 300)

		{

			double dist2 = Math.abs(A10 * venus[0] + B10 * venus[1] + C10
					* venus[2] - D10)
					/ Math.sqrt((A10 * A10) + (B10 * B10) + (C10 * C10));
			if(A10*venus[0]+B10*venus[1]+C10*venus[2]-D10 <=0){

			if (dist2 <0.8) {

				bounce++;
				k += 1;

				System.out.println("strike" + k);

				b2 = -b2;
				a2 += b2;

				f2 = -f2;
				e2 += f2;

				d2 = -d2;
				c2 += d2;
			}
			}
		}

		if (distance(jupitor, ctr) < 250)
		{

			double dist3 = Math.abs(A10 * jupitor[0] + B10 * jupitor[1] + C10
					* jupitor[2] - D10)
					/ Math.sqrt((A10 * A10) + (B10 * B10) + (C10 * C10));
			if(A10*jupitor[0]+B10*jupitor[1]+C10*jupitor[2]-D10 <=0){

			if (dist3 < 28) {

				bounce++;
				k += 1;

				System.out.println("strike" + k);

				b3 = -b3;
				a3 += b3;

				f3 = -f3;
				e3 += f3;

				d3 = -d3;
				c3 += d3;
			}
			}
		}

		// bounce saturn from the paddle

		if (distance(saturn, ctr) < 250)
		{

			double dist4 = Math.abs(A10 * saturn[0] + B10 * saturn[1] + C10
					* saturn[2] - D10)
					/ Math.sqrt((A10 * A10) + (B10 * B10) + (C10 * C10));
			if(A10*saturn[0]+B10*saturn[1]+C10*saturn[2]-D10 <=0){

			if (dist4 < 28) {

				bounce++;
			
			
				b4 = -b4;
				a4 += b4;

				f4 = -f4;
				e4 += f4;

				d4 = -d4;
				c4 += d4;
			}
			}
		}

	
		depth = (cnt / 100) % 6;
		cnt++;
	
		// Equation for the BOX :right plane

		double A, B, C, D;
		double x1, x2, x3, y1, y2, y3, z1, z2, z3;

		x1 = WIDTH / 3;
		x2 = -WIDTH / 4 + (2 * (WIDTH / 3));
		x3 = WIDTH / 3;

		y1 = -HEIGHT / 3;
		y2 = -HEIGHT / 4;
		y3 = HEIGHT / 3;

		z1 = 400;
		z2 = -400;
		z3 = 400;

		A = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
		B = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
		C = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
		D = x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3
				* (y1 * z2 - y2 * z1);

		if ((A * (earthC[0] + 32) + B * (earthC[1]) + C * (earthC[2]) - D) < 0) {
			// System.out.println("in right plane");

			b = -b;
			a += b;

		}
		// for 2nd ball

		if ((A * (mars[0] + 32) + B * (mars[1]) + C * (mars[2]) - D) < 0) {

			// System.out.println("in right plane");

			b1 = -b1;
			a1 += b1;

		}

		// 3rd ball

		if ((A * (venus[0] + 32) + B * (venus[1]) + C * (venus[2]) - D) < 0) {
			// System.out.println("in right plane");

			b2 = -b2;
			a2 += b2;

		}

		// 4th ball

		if ((A * (jupitor[0] + 32) + B * (jupitor[1]) + C * (jupitor[2]) - D) < 0) {
			// System.out.println("in right plane");

			b3 = -b3;
			a3 += b3;

		}

		// 5th ball

		if ((A * (saturn[0] + 32) + B * (saturn[1]) + C * (saturn[2]) - D) < 0) {
			// System.out.println("in right plane");

			b4 = -b4;
			a4 += b4;

		}

		// BOX Equation: left plane

		double A1, B1, C1, D1;
		double x4, x5, x6, y4, y5, y6, z4, z5, z6;

		x4 = -WIDTH / 3;
		x5 = -WIDTH / 4;
		x6 = -WIDTH / 3;

		y4 = -HEIGHT / 3;
		y5 = -HEIGHT / 4;
		y6 = HEIGHT / 3;

		z4 = 400;
		z5 = -400;
		z6 = 400;

		A1 = y4 * (z5 - z6) + y5 * (z6 - z4) + y6 * (z4 - z5);
		B1 = z4 * (x5 - x6) + z5 * (x6 - x4) + z6 * (x4 - x5);
		C1 = x4 * (y5 - y6) + x5 * (y6 - y4) + x6 * (y4 - y5);
		D1 = x4 * (y6 * z6 - y6 * z5) + x5 * (y6 * z4 - y4 * z6) + x6
				* (y4 * z5 - y5 * z4);

		if ((A1 * (earthC[0] - 135) + B1 * (earthC[1]) + C1 * (earthC[2]) - D1) >= 0) {

			// System.out.println("In the left plane");
			b = -b;
			a += b;

		}

		a += b;

		// for 2nd ball
		if ((A1 * (mars[0] - 135) + B1 * (mars[1]) + C1 * (mars[2]) - D1) >= 0) {

			// System.out.println("In the left plane");
			b1 = -b1;
			a1 += b1;

		}
		a1 += b1;

		// for 3rd ball
		if ((A1 * (venus[0] - 135) + B1 * (venus[1]) + C1 * (venus[2]) - D1) >= 0) {

			// System.out.println("In the left plane");
			b2 = -b2;
			a2 += b2;

		}
		a2 += b2;

		// for 4th ball

		if ((A1 * (jupitor[0] - 135) + B1 * (jupitor[1]) + C1 * (jupitor[2]) - D1) >= 0) {

			// System.out.println("In the left plane");
			b3 = -b3;
			a3 += b3;

		}
		a3 += b3;

		// for 5th ball

		if ((A1 * (saturn[0] - 135) + B1 * (saturn[1]) + C1 * (saturn[2]) - D1) >= 0) {

			// System.out.println("In the left plane");
			b4 = -b4;
			a4 += b4;

		}
		a4 += b4;

		
		//BOX Equation: back plane

		double A3, B3, C3, D3;
		double x7, y7, z7;

		x7 = -WIDTH / 4 + (2 * (WIDTH / 3));
		y7 = -HEIGHT / 4 + (2 * (HEIGHT / 3));
		z7 = -400;

		A3 = y2 * (z5 - z7) + y5 * (z7 - z2) + y7 * (z2 - z5);
		B3 = z2 * (x5 - x7) + z5 * (x7 - x2) + z7 * (x2 - x5);
		C3 = x2 * (y5 - y7) + x5 * (y7 - y2) + x7 * (y2 - y5);
		D3 = x2 * (y5 * z7 - y7 * z5) + x5 * (y7 * z2 - y2 * z7) + x7
				* (y2 * z5 - y5 * z2);

		if ((A3 * (earthC[0]) + B3 * (earthC[1]) + C3 * (earthC[2]) - D3) < 0) {

			// System.out.println("In the back  plane");
			d = -d;
			c += d;

		}
		// for 2nd ball
		if ((A3 * (mars[0]) + B3 * (mars[1]) + C3 * (mars[2]) - D3) < 0) {

			// System.out.println("In the back  plane");
			d1 = -d1;
			c1 += d1;

		}

		// 3rd ball

		if ((A3 * (venus[0]) + B3 * (venus[1]) + C3 * (venus[2]) - D3) < 0) {

			// System.out.println("In the back  plane");
			d2 = -d2;
			c2 += d2;

		}

		// 4th ball

		if ((A3 * (jupitor[0]) + B3 * (jupitor[1]) + C3 * (jupitor[2]) - D3) < 0) {

			// System.out.println("In the back  plane");
			d3 = -d3;
			c3 += d3;

		}

		// 5th ball

		if ((A3 * (saturn[0]) + B3 * (saturn[1]) + C3 * (saturn[2]) - D3) < 0) {

			// System.out.println("In the back  plane");
			d4 = -d4;
			c4 += d4;

		}

	

		// BOX Equation:Front Plane

		double A2, B2, C2, D2;

		A2 = y1 * (z3 - z4) + y3 * (z4 - z1) + y4 * (z1 - z3);
		B2 = z1 * (x3 - x4) + z3 * (x4 - x1) + z4 * (x1 - x3);
		C2 = x1 * (y3 - y4) + x3 * (y4 - y1) + x4 * (y1 - y3);
		D2 = x1 * (y3 * z4 - y4 * z3) + x3 * (y4 * z1 - y1 * z4) + x4
				* (y1 * z3 - y3 * z1);

		if ((A2 * earthC[0] + B2 * earthC[1] + C2 * (earthC[2] - 50) - D2) > 0) {

			// System.out.println("In the front  plane");

			d = -d;
			c += d;

		}
		c += d;

		// for 2nd ball

		if ((A2 * mars[0] + B2 * mars[1] + C2 * (mars[2] - 50) - D2) < 0) {

			// System.out.println("In the front  plane");

			d1 = -d1;
			c1 += d1;

		}

		// rotation along another axis
		c1 += d1;

		// 3rd ball

		if ((A2 * venus[0] + B2 * venus[1] + C2 * (venus[2] - 50) - D2) < 0) {

			// System.out.println("In the front  plane");

			d2 = -d2;
			c2 += d2;

		}

		// 4th ball

		if ((A2 * jupitor[0] + B2 * jupitor[1] + C2 * (jupitor[2] - 50) - D2) < 0) {

			// System.out.println("In the front  plane");

			d3 = -d3;
			c3 += d3;

		}

		// rotation along another axis
		c3 += d3;

		// 5th ball

		if ((A2 * saturn[0] + B2 * saturn[1] + C2 * (saturn[2] - 50) - D2) < 0) {

			// System.out.println("In the front  plane");

			d4 = -d4;
			c4 += d4;

		}

		// rotation along another axis
		c4 += d4;

		

		// BOX Equation:top frame

		double A4, B4, C4, D4;

		A4 = y3 * (z6 - z7) + y6 * (z7 - z3) + y7 * (z3 - z6);
		B4 = z3 * (x6 - x7) + z6 * (x7 - x3) + z7 * (x3 - x6);
		C4 = x3 * (y6 - y7) + x6 * (y7 - y3) + x7 * (y3 - y6);
		D4 = x3 * (y6 * z7 - y7 * z6) + x6 * (y7 * z3 - y3 * z7) + x7
				* (y3 * z6 - y6 * z3);

		if ((A4 * (earthC[0]) + B4 * (earthC[1] + 30) + C4 * (earthC[2]) - D4) > 0) {

			// System.out.println("In the top  plane");

			f = -f;
			e += f;

		}

		// for 2nd ball

		if ((A4 * (mars[0]) + B4 * (mars[1] + 30) + C4 * (mars[2]) - D4) > 0) {

			// System.out.println("In the top  plane");

			f1 = -f1;
			e1 += f1;

		}

		// for 3rd ball

		if ((A4 * (venus[0]) + B4 * (venus[1] + 30) + C4 * (venus[2]) - D4) > 0) {

			// System.out.println("In the top  plane");

			f2 = -f2;
			e2 += f2;

		}

		// for 4th ball

		if ((A4 * (jupitor[0]) + B4 * (jupitor[1] + 30) + C4 * (jupitor[2]) - D4) > 0) {

			// System.out.println("In the top  plane");

			f3 = -f3;
			e3 += f3;

		}

		// for 5th ball

		if ((A4 * (saturn[0]) + B4 * (saturn[1] + 30) + C4 * (saturn[2]) - D4) > 0) {

			// System.out.println("In the top  plane");

			f4 = -f4;
			e4 += f4;

		}


		// BOX Equation:bottom frame
		double A5, B5, C5, D5;

		A5 = y1 * (z2 - z5) + y2 * (z5 - z1) + y5 * (z1 - z2);
		B5 = z1 * (x2 - x5) + z2 * (x5 - x1) + z5 * (x1 - x2);
		C5 = x1 * (y2 - y5) + x2 * (y5 - y1) + x5 * (y1 - y2);
		D5 = x1 * (y2 * z5 - y5 * z2) + x2 * (y5 * z1 - y1 * z5) + x5
				* (y1 * z2 - y2 * z1);

		if ((A5 * (earthC[0]) + B5 * (earthC[1] - 20) + C5 * (earthC[2]) - D5) > 0)

		{

			// System.out.println("In the bottom  plane");

			f = -f;
			e += f;

		}
		e += f;

		// for 2nd ball
		if ((A5 * (mars[0]) + B5 * (mars[1] - 20) + C5 * (mars[2]) - D5) > 0) {

			// System.out.println("In the bottom  plane");

			f1 = -f1;
			e1 += f1;

		}
		e1 += f1;
		// 3rd ball

		if ((A5 * (venus[0]) + B5 * (venus[1] - 20) + C5 * (venus[2]) - D5) > 0)

		{

			// System.out.println("In the bottom  plane");

			f2 = -f2;
			e2 += f2;

		}
		e2 += f2;

		// 4th ball

		if ((A5 * (jupitor[0]) + B5 * (jupitor[1] - 20) + C5 * (jupitor[2]) - D5) > 0)

		{

			// System.out.println("In the bottom  plane");

			f3 = -f3;
			e3 += f3;

		}
		e3 += f3;

		// 5th ball

		if ((A5 * (saturn[0]) + B5 * (saturn[1] - 20) + C5 * (saturn[2]) - D5) > 0)

		{

			// System.out.println("In the bottom  plane");

			f4 = -f4;
			e4 += f4;

		}
		e4 += f4;

	
		
		//Display the String on the Frame .
		
		
		myMaterialColor(blackish, twhite, twhite, blackish);

		// GLUT naming of the objects and program
		gl.glRasterPos3d(0, -HEIGHT / 3 - 30, 0);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Bounce  :" + bounce);

		gl.glRasterPos3d(-WIDTH / 4 + 50, -HEIGHT / 3 - 60, 0);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18,
				"Project CS652: Bouncing balls Game ");

		
		
		
		// Draw the Box 

		gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glBegin(GL.GL_QUADS);
		// gl.glPointSize(20);
		// gl.glColor3f(0.0f, 1.0f, 0.0f); // Green

		// Front Plane
		/*
		 * gl.glColor3f(0.0f, 1.0f, 0.0f); gl.glVertex3d(-WIDTH / 3, -HEIGHT /
		 * 3, 400); gl.glVertex3d(WIDTH / 3, -HEIGHT / 3, 400);
		 * gl.glVertex3d(WIDTH / 3, HEIGHT / 3, 400); gl.glVertex3d(-WIDTH / 3,
		 * HEIGHT / 3, 400);
		 */

		// Back Plane
		// gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3d(-WIDTH / 3, -HEIGHT / 3, -400);

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3d(WIDTH / 3, -HEIGHT / 3, -400);

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3d(WIDTH / 3, HEIGHT / 3, -400);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3d(-WIDTH / 3, HEIGHT / 3, -400);

		// Left side

		
		gl.glNormal3f(-WIDTH / 3, -HEIGHT / 3, -400);
		gl.glColor3f(1.0f, 0.0f, 0.0f);

		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3d(-WIDTH / 3, -HEIGHT / 3, 400);

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3d(-WIDTH / 4, -HEIGHT / 4, -400);

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3d(-WIDTH / 4, -HEIGHT / 4 + ((2 * HEIGHT / 3)), -400);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3d(-WIDTH / 3, HEIGHT / 3, 400);

		gl.glBindTexture(GL.GL_TEXTURE_2D, EARTH_TEX[0]);
		
		// Right Plane
		
		gl.glNormal3f(WIDTH / 3, -HEIGHT / 3, 400);

		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3d(WIDTH / 3, -HEIGHT / 3, 400);

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3d(-WIDTH / 4 + (2 * (WIDTH / 3)), -HEIGHT / 4, -400);

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3d(-WIDTH / 4 + (2 * (WIDTH / 3)), -HEIGHT / 4
				+ (2 * (HEIGHT / 3)), -400);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3d(WIDTH / 3, HEIGHT / 3, 400);

		// Top plane
		
		gl.glNormal3f(WIDTH / 3, HEIGHT / 3, -400);

		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3d(WIDTH / 3, HEIGHT / 3, 400);

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3d(-WIDTH / 4 + (2 * (WIDTH / 3)), -HEIGHT / 4
				+ (2 * (HEIGHT / 3)), -400);

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3d(-WIDTH / 4, -HEIGHT / 4 + (2 * (HEIGHT / 3)), -400);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3d(-WIDTH / 3, HEIGHT / 3, 400);

		// Bottom Frame

		
		gl.glNormal3f(WIDTH / 3, -HEIGHT / 3, -400);

		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3d(WIDTH / 3, -HEIGHT / 3, 400);

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3d(-WIDTH / 4 + (2 * (WIDTH / 3)), -HEIGHT / 4, -400);

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3d(-WIDTH / 4, -HEIGHT / 4, -400);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3d(-WIDTH / 3, -HEIGHT / 3, 400);

		gl.glEnd();

		// box end

		
		//Draw the Spheres ( Planets in this game )
		
		gl.glEnable(GL.GL_BLEND); //Enable the blending 
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); //blending factor

		// master push
		gl.glPushMatrix();

		// rotAngle+=1;
		gl.glPushMatrix();

		gl.glRotated(rotAngle, 0.5f, 0.3f, 0.0f);
	
		gl.glPushMatrix();

		gl.glScalef(WIDTH / 40f, WIDTH / 40f, WIDTH / 40f);
		// gl.glRotated(45, 0.5f, 0.3f, 0.0f);

		gl.glTranslated(a, e, c);

	
		myMaterialColor(tgreen, tgreen, tgreen, tgreen);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, origin, 0);

		drawSphere();

		gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, currM, 0);
		earthC[0] = currM[12];
		earthC[1] = currM[13];
		earthC[2] = currM[14];
		gl.glPopMatrix();

		gl.glPopMatrix();

		// for mars
		gl.glPushMatrix();
		gl.glRotated(rotAngle1, 1f, 0f, 0f);
		gl.glTranslated(-WIDTH / 3 + 55, -HEIGHT / 3 + 58, 0);

		gl.glPushMatrix();
		gl.glScalef(WIDTH / 40f, WIDTH / 40f, WIDTH / 40f);

		gl.glTranslated(a1, e1, c1);
	
		myMaterialColor(tred, tred, tred, tred);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, origin1, 0);
		drawSphere();
		gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, currM, 0);
		mars[0] = currM[12];
		mars[1] = currM[13];
		mars[2] = currM[14];
		gl.glPopMatrix();

		gl.glPopMatrix();

		// for venus

		gl.glPushMatrix();
		gl.glRotated(rotAngle2, 1f, 0f, 0f);
		gl.glTranslated(WIDTH / 3 - 60, -HEIGHT / 3 + 60, 0);

		gl.glPushMatrix();
		gl.glScalef(WIDTH / 40f, WIDTH / 40f, WIDTH / 40f);

		gl.glTranslated(a2, e2, c2);

		myMaterialColor(tblue, tblue, tblue, tblue);
		// myMaterialColor(blueish, blueish, blue, blueish);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, origin2, 0);
		drawSphere();

		gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, currM, 0);
		venus[0] = currM[12];
		venus[1] = currM[13];
		venus[2] = currM[14];
		gl.glPopMatrix();

		gl.glPopMatrix();

		gl.glDisable(GL.GL_BLEND);

		// for jupitor

		gl.glPushMatrix();
		gl.glRotated(rotAngle3, 1f, 0f, 0f);
		gl.glTranslated(WIDTH / 3 - 200, -HEIGHT / 3 + 160, 0);

		gl.glPushMatrix();
		gl.glScalef(WIDTH / 40f, WIDTH / 40f, WIDTH / 40f);

		gl.glTranslated(a3, e3, c3);

		myMaterialColor(blackish, blackish, black, black);
		drawSphere();

		gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, currM, 0);
		jupitor[0] = currM[12];
		jupitor[1] = currM[13];
		jupitor[2] = currM[14];
		gl.glPopMatrix();

		gl.glPopMatrix();

		// for saturn

		gl.glPushMatrix();
		 gl.glRotated(rotAngle4, 1f, 0f, 0f);
		gl.glTranslated(WIDTH / 3, HEIGHT / 3, 0);

		gl.glPushMatrix();
		gl.glScalef(WIDTH / 40f, WIDTH / 40f, WIDTH / 40f);

		gl.glTranslated(a4, e4, c4);

		myMaterialColor(blackish, blackish, black, cyan);
		drawSphere();

		gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, currM, 0);
		saturn[0] = currM[12];
		saturn[1] = currM[13];
		saturn[2] = currM[14];
		gl.glPopMatrix();

		gl.glPopMatrix();

		gl.glPopMatrix(); 
		
		
		// master pop ; Sphere declaration ends.

	
		// reset the rotation angle to zero.
		rotAngle = 0;
		rotAngle1 = 0;
		rotAngle2 = 0;
		rotAngle3 = 0;
		rotAngle4 = 0;
		
		if (distance(mars, earthC) < 30) {
			// type the result
			System.out.println("Collosion Occured");
			rotAngle = 90;
			rotAngle1 = -90;
		}

		if (distance(mars, venus) < 30) {

			rotAngle1 = 90;
			rotAngle2 = -90;
		}

		if (distance(venus, earthC) < 35) {

			rotAngle = 90;
			rotAngle2 = -90;
		}

		if (distance(jupitor, earthC) < 35) {

			rotAngle3 = 90;
			rotAngle = -90;
		}

		if (distance(jupitor, mars) < 35) {

			rotAngle3 = 90;
			rotAngle1 = -90;
		}

		if (distance(jupitor, venus) < 35) {

			rotAngle3 = 90;
			rotAngle2 = -90;
		}

		
		try {
			Thread.sleep(10);
		} catch (Exception ignore) {
		}

	}

	double distance(double x0, double y0, double z0, double x1, double y1,
			double z1) {
		return (Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0)
				+ (z1 - z0) * (z1 - z0)));
	}


	// cros product of two vectors
	public double crossprod(double[] a, float[] earthC2) {

		double v[] = new double[3];
		v[0] = a[1] * earthC2[2] - a[2] * earthC2[1];
		v[1] = a[2] * earthC2[0] - a[0] * earthC2[2];
		v[2] = a[0] * earthC2[1] - a[1] * earthC2[0];

		return (Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]));
	}


	public void reflect(double v1[], double n[], double v2[]) {

		// v2 = 2*dot(v1, n)*n + v1
		for (int i = 0; i < 3; i++) {
			v2[i] = 2 * dotprod(v1, n) * n[i] - v1[i];
		}
	}

	public double dotprod(double[] a, double[] b) {

		return (a[0] * b[0] + a[1] * b[1] + a[2] * b[2]);
	}

	private void subdivideSphere(double v1[], double v2[], double v3[],
			long depth) {
		double v12[] = new double[3];
		double v23[] = new double[3];
		double v31[] = new double[3];
		int i;

		if (depth == 0) {
			gl.glColor3d(v1[0] * v1[0], v2[1] * v2[1], v3[2] * v3[2]);
			drawtriangle(v1, v2, v3);
			return;
		}

		for (i = 0; i < 3; i++) {
			v12[i] = v1[i] + v2[i];
			v23[i] = v2[i] + v3[i];
			v31[i] = v3[i] + v1[i];
		}

		normalize(v12);
		normalize(v23);
		normalize(v31);
		subdivideSphere(v1, v12, v31, depth - 1);
		subdivideSphere(v2, v23, v12, depth - 1);
		subdivideSphere(v3, v31, v23, depth - 1);
		subdivideSphere(v12, v23, v31, depth - 1);
	}

	public void drawSphere() {
		subdivideSphere(sVdata[0], sVdata[1], sVdata[2], depth);
		subdivideSphere(sVdata[0], sVdata[2], sVdata[4], depth);
		subdivideSphere(sVdata[0], sVdata[4], sVdata[5], depth);
		subdivideSphere(sVdata[0], sVdata[5], sVdata[1], depth);

		subdivideSphere(sVdata[3], sVdata[1], sVdata[5], depth);
		subdivideSphere(sVdata[3], sVdata[5], sVdata[4], depth);
		subdivideSphere(sVdata[3], sVdata[4], sVdata[2], depth);
		subdivideSphere(sVdata[3], sVdata[2], sVdata[1], depth);
	}

	public void normalize(double vector[]) {
		float d = (float) Math.sqrt(vector[0] * vector[0] + vector[1]
				* vector[1] + vector[2] * vector[2]);

		if (d == 0) {
			// System.out.println("0 length vector: normalize().");
			return;
		}
		vector[0] /= d;
		vector[1] /= d;
		vector[2] /= d;
	}

	public void drawtriangle(double[] v1, double[] v2, double[] v3) {
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glVertex3dv(v1, 0);
		gl.glVertex3dv(v2, 0);
		gl.glVertex3dv(v3, 0);
		gl.glEnd();
	}

	float distance(float[] c1, float[] c2) {
		float tmp = (c2[0] - c1[0]) * (c2[0] - c1[0]) + (c2[1] - c1[1])
				* (c2[1] - c1[1]) + (c2[2] - c1[2]) * (c2[2] - c1[2]);

		return ((float) Math.sqrt(tmp));
	}

	// for new points
	double distance(double[] c1, float[] c2) {
		double tmp = (c2[0] - c1[0]) * (c2[0] - c1[0]) + (c2[1] - c1[1])
				* (c2[1] - c1[1]) + (c2[2] - c1[2]) * (c2[2] - c1[2]);

		return (Math.sqrt(tmp));
	}

	double distance(float[] c1, double[] c2) {
		double tmp = (c2[0] - c1[0]) * (c2[0] - c1[0]) + (c2[1] - c1[1])
				* (c2[1] - c1[1]) + (c2[2] - c1[2]) * (c2[2] - c1[2]);

		return ((float) Math.sqrt(tmp));
	}

	// Read image

	public void readImage(String fileName) {
		File f = new File(fileName);
		BufferedImage bufimg;

		try {
			// read the image into BufferredImage structure
			bufimg = ImageIO.read(f);
			imgW = bufimg.getWidth();
			imgH = bufimg.getHeight();
			imgType = bufimg.getType();
			System.out.println(fileName + " -- BufferedImage WIDTH&HEIGHT: "
					+ imgW + ", " + imgH);
			System.out.println("BufferedImage type TYPE_3BYTE_BGR 5; GRAY 10: "
					+ imgType);
			// TYPE_BYTE_GRAY 10
			// TYPE_3BYTE_BGR 5

			// retrieve the pixel array in raster's databuffer
			Raster raster = bufimg.getData();

			DataBufferByte dataBufByte = (DataBufferByte) raster
					.getDataBuffer();
			img = dataBufByte.getData();
			System.out.println("Image data's type TYPE_BYTE 0: "
					+ dataBufByte.getDataType());
			// TYPE_BYTE 0

		} catch (IOException ex) {
			System.exit(1);
		}
	}

	// Texture initialization /function

	public void initTexture() {

		// gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // Perspective
																	// correction

		// gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE,
		// GL.GL_REPLACE);
		// gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glGenTextures(1, IntBuffer.wrap(EARTH_TEX));
		gl.glBindTexture(GL.GL_TEXTURE_2D, EARTH_TEX[0]);

		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_NEAREST);
		readImage("neo_2.jpg"); // read the image to img[]

		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB8, imgW, imgH, 0,
				GL.GL_BGR, GL.GL_UNSIGNED_BYTE, ByteBuffer.wrap(img));

	}

	public void myMaterialColor(float myA[], float myD[], float myS[],
			float myE[]) {

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, myA, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, myD, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, myS, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, myE, 0);
	}

	// mouse moved

	public void mouseMoved(MouseEvent me) {

		int mouseX, mouseY;
		mouseX = me.getX();
		mouseY = me.getY();
	
		double A1, B1, C1, D1;
		double x4, x5, x6, y4, y5, y6, z4, z5, z6;

		double A, B, C, D;
		double x1, x2, x3, y1, y2, y3, z1, z2, z3;

		x1 = WIDTH / 3;
		x2 = -WIDTH / 4 + (2 * (WIDTH / 3));
		x3 = WIDTH / 3;

		y1 = -HEIGHT / 3;
		y2 = -HEIGHT / 4;
		y3 = HEIGHT / 3;

		z1 = 400;
		z2 = -400;
		z3 = 400;

		x4 = -WIDTH / 3;
		x5 = -WIDTH / 4;
		x6 = -WIDTH / 3;

		y4 = -HEIGHT / 3;
		y5 = -HEIGHT / 4;
		y6 = HEIGHT / 3;

		z4 = 400;
		z5 = -400;
		z6 = 400;

	
		double x7, y7, z7;

		x7 = -WIDTH / 4 + (2 * (WIDTH / 3));
		y7 = -HEIGHT / 4 + (2 * (HEIGHT / 3));
		z7 = -400;

		if (mouseX <= mx) {

			// left plane

			A1 = y4 * (z5 - z6) + y5 * (z6 - z4) + y6 * (z4 - z5);
			B1 = z4 * (x5 - x6) + z5 * (x6 - x4) + z6 * (x4 - x5);
			C1 = x4 * (y5 - y6) + x5 * (y6 - y4) + x6 * (y4 - y5);
			D1 = x4 * (y6 * z6 - y6 * z5) + x5 * (y6 * z4 - y4 * z6) + x6
					* (y4 * z5 - y5 * z4);

			if ((A1 * (point[0][0] - 120) + B1 * (point[0][1]) + C1
					* (point[0][2]) - D1) > 0) {

				point[0][0] -= move_fact;

				point[1][0] -= move_fact;

				point[2][0] -= move_fact;

				point[3][0] -= move_fact;

			}


		}

		// left move
		else {

			// right plane

			A = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
			B = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
			C = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
			D = x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3
					* (y1 * z2 - y2 * z1);

			if ((A * (point[3][0] + 20) + B * (point[3][1]) + C * (point[3][2]) - D) < 0) {

				point[0][0] += move_fact;

				point[1][0] += move_fact;

				point[2][0] += move_fact;

				point[3][0] += move_fact;

			}

		}
		// right move


		if (mouseY <= my) {
			double A4, B4, C4, D4;

			A4 = y3 * (z6 - z7) + y6 * (z7 - z3) + y7 * (z3 - z6);
			B4 = z3 * (x6 - x7) + z6 * (x7 - x3) + z7 * (x3 - x6);
			C4 = x3 * (y6 - y7) + x6 * (y7 - y3) + x7 * (y3 - y6);
			D4 = x3 * (y6 * z7 - y7 * z6) + x6 * (y7 * z3 - y3 * z7) + x7
					* (y3 * z6 - y6 * z3);

			if (point[1][1] < HEIGHT / 3) {

				if ((A4 * (point[1][0]) + B4 * (point[1][1]) + C4
						* (point[1][2]) - D4) < 0)
					;
				{
					point[0][1] += move_fact;

					point[1][1] += move_fact;

					point[2][1] += move_fact;

					point[3][1] += move_fact;

				}
			}

		//	System.out.println("my value :" + my);

		}

		else {
			double A5, B5, C5, D5;

			A5 = y1 * (z2 - z5) + y2 * (z5 - z1) + y5 * (z1 - z2);
			B5 = z1 * (x2 - x5) + z2 * (x5 - x1) + z5 * (x1 - x2);
			C5 = x1 * (y2 - y5) + x2 * (y5 - y1) + x5 * (y1 - y2);
			D5 = x1 * (y2 * z5 - y5 * z2) + x2 * (y5 * z1 - y1 * z5) + x5
					* (y1 * z2 - y2 * z1);

			if ((A5 * (point[0][0] + 20) + B5 * (point[0][1]) + C5
					* (point[0][2]) - D5) > 0) {

				point[0][1] -= move_fact;

				point[1][1] -= move_fact;

				point[2][1] -= move_fact;

				point[3][1] -= move_fact;

			}
		}

		mx = mouseX;
		my = mouseY;

	}

	public static void main(String args[]) {
		Project_nsingh6_Spring2012 f1 = new Project_nsingh6_Spring2012();
		f1.setTitle("Project-CS652: Bouncing Balls Game");
		f1.setSize(WIDTH, HEIGHT);
		f1.setVisible(true);

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// key events

	public void keyPressed(KeyEvent e) {


		double A1, B1, C1, D1;
		double x4, x5, x6, y4, y5, y6, z4, z5, z6;

		double A, B, C, D;
		double x1, x2, x3, y1, y2, y3, z1, z2, z3;

		x1 = WIDTH / 3;
		x2 = -WIDTH / 4 + (2 * (WIDTH / 3));
		x3 = WIDTH / 3;

		y1 = -HEIGHT / 3;
		y2 = -HEIGHT / 4;
		y3 = HEIGHT / 3;

		z1 = 400;
		z2 = -400;
		z3 = 400;

		x4 = -WIDTH / 3;
		x5 = -WIDTH / 4;
		x6 = -WIDTH / 3;

		y4 = -HEIGHT / 3;
		y5 = -HEIGHT / 4;
		y6 = HEIGHT / 3;

		z4 = 400;
		z5 = -400;
		z6 = 400;

		double A3, B3, C3, D3;
		double x7, y7, z7;

		x7 = -WIDTH / 4 + (2 * (WIDTH / 3));
		y7 = -HEIGHT / 4 + (2 * (HEIGHT / 3));
		z7 = -400;

		if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {

			A1 = y4 * (z5 - z6) + y5 * (z6 - z4) + y6 * (z4 - z5);
			B1 = z4 * (x5 - x6) + z5 * (x6 - x4) + z6 * (x4 - x5);
			C1 = x4 * (y5 - y6) + x5 * (y6 - y4) + x6 * (y4 - y5);
			D1 = x4 * (y6 * z6 - y6 * z5) + x5 * (y6 * z4 - y4 * z6) + x6
					* (y4 * z5 - y5 * z4);

			// System.out.println("Key UP is pressed");
			if ((A1 * (point[0][0] - 120) + B1 * (point[0][1]) + C1
					* (point[0][2]) - D1) > 0) {

				point[0][0] -= move_fact;

				point[1][0] -= move_fact;

				point[2][0] -= move_fact;

				point[3][0] -= move_fact;

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {

			A = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
			B = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
			C = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
			D = x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3
					* (y1 * z2 - y2 * z1);

			if ((A * (point[3][0] + 20) + B * (point[3][1]) + C * (point[3][2]) - D) < 0) {

				point[0][0] += move_fact;

				point[1][0] += move_fact;

				point[2][0] += move_fact;

				point[3][0] += move_fact;

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {

			double A5, B5, C5, D5;

			A5 = y1 * (z2 - z5) + y2 * (z5 - z1) + y5 * (z1 - z2);
			B5 = z1 * (x2 - x5) + z2 * (x5 - x1) + z5 * (x1 - x2);
			C5 = x1 * (y2 - y5) + x2 * (y5 - y1) + x5 * (y1 - y2);
			D5 = x1 * (y2 * z5 - y5 * z2) + x2 * (y5 * z1 - y1 * z5) + x5
					* (y1 * z2 - y2 * z1);

			if ((A5 * (point[0][0]) + B5 * (point[0][1]) + C5 * (point[0][2]) - D5) > 0) {

				point[0][1] -= move_fact;

				point[1][1] -= move_fact;

				point[2][1] -= move_fact;

				point[3][1] -= move_fact;

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			double A4, B4, C4, D4;
			A4 = y3 * (z6 - z7) + y6 * (z7 - z3) + y7 * (z3 - z6);
			B4 = z3 * (x6 - x7) + z6 * (x7 - x3) + z7 * (x3 - x6);
			C4 = x3 * (y6 - y7) + x6 * (y7 - y3) + x7 * (y3 - y6);
			D4 = x3 * (y6 * z7 - y7 * z6) + x6 * (y7 * z3 - y3 * z7) + x7
					* (y3 * z6 - y6 * z3);

			if (point[1][1] < HEIGHT / 3) {

				if ((A4 * (point[1][0]) + B4 * (point[1][1] + 20) + C4
						* (point[1][2]) - D4) > 0)
					;
				{
					point[0][1] += move_fact;

					point[1][1] += move_fact;

					point[2][1] += move_fact;

					point[3][1] += move_fact;

				}
			}
		}

		// front plane so increment z value.

		if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {

			double A2, B2, C2, D2;

			A2 = y1 * (z3 - z4) + y3 * (z4 - z1) + y4 * (z1 - z3);
			B2 = z1 * (x3 - x4) + z3 * (x4 - x1) + z4 * (x1 - x3);
			C2 = x1 * (y3 - y4) + x3 * (y4 - y1) + x4 * (y1 - y3);
			D2 = x1 * (y3 * z4 - y4 * z3) + x3 * (y4 * z1 - y1 * z4) + x4
					* (y1 * z3 - y3 * z1);

			if ((A2 * (point[0][0]) + B2 * (point[0][1]) + C2 * (point[0][2]) - D2) < 0) {

				point[0][2] += move_fact;

				point[1][2] += move_fact;

				point[2][2] += move_fact;

				point[3][2] += move_fact;

			}
		}

		if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {

			A3 = y2 * (z5 - z7) + y5 * (z7 - z2) + y7 * (z2 - z5);
			B3 = z2 * (x5 - x7) + z5 * (x7 - x2) + z7 * (x2 - x5);
			C3 = x2 * (y5 - y7) + x5 * (y7 - y2) + x7 * (y2 - y5);
			D3 = x2 * (y5 * z7 - y7 * z5) + x5 * (y7 * z2 - y2 * z7) + x7
					* (y2 * z5 - y5 * z2);

			if ((A3 * (point[1][0]) + B3 * (point[1][1]) + C3 * (point[1][2]) - D3) < 0) {

				point[0][2] -= move_fact;

				point[1][2] -= move_fact;

				point[2][2] -= move_fact;

				point[3][2] -= move_fact;
			}

		}

		// rotAngleglu=rotAngleglu+1;rotAngleglu=rotAngleglu+1;

		if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
			rotAngleglu = rotAngleglu + 10;
		}

		if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
			rotAngleglu = rotAngleglu - 10;
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			rotAngleglu1 = rotAngleglu1 + 10;
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			rotAngleglu1 = rotAngleglu1 - 10;
		}

		if (e.getKeyCode() == KeyEvent.VK_W) {
			rotAngleglu2 = rotAngleglu2 + 10;
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			rotAngleglu2 = rotAngleglu2 - 10;
		}

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		 }

	// }

	
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
