
import java.awt.*;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;


public class HW4_nsingh6_BoxRot extends Frame implements GLEventListener {
	
	
	static int HEIGHT=800;
	static int WIDTH=800;
	static Animator animator;
	GL gl;
	GLCanvas canvas;
	 float angle1 = 0;  // rotational angle
	   static float angleCube = 0;       //Rotational Angle
	    float angletri = 0;//Rotational Angle 
	   static float speedCube = -1.5f;
	   GLU glu;
	   
	public HW4_nsingh6_BoxRot(){
		canvas=new GLCanvas();
		
		canvas.addGLEventListener(this);
		this.add(canvas,BorderLayout.CENTER);
		
		gl=canvas.getGL();
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				animator.stop(); // stop animation
				System.exit(0);
			}
		});
		
	}
	
	
	public void init(GLAutoDrawable drawable){
		
	
		animator = new Animator(canvas);
		animator.start(); // start animator thread
		glu = new GLU();
		
	}
	
	public void reshape(GLAutoDrawable drawable,int x,int y,int w,int h){
		
		
		WIDTH = w;
	    HEIGHT = h;
	    
	    
	    
	    gl.glEnable(GL.GL_DEPTH_TEST);
	    	
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		
		
		gl.glOrtho(-w/2, w/2, -h/2, h/2, -w, w);
	      // Enable the model-view transform
	      
	      
	   gl.glMatrixMode(GL.GL_MODELVIEW);
	      gl.glLoadIdentity(); // reset
	      

	}
	
	public void display(GLAutoDrawable drawable){
		
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
				

			
			 gl.glPushMatrix();
		
			 gl.glRotatef(angleCube,0f,1f, 0f);	
			  gl.glTranslatef(WIDTH/5, 0f, 0f);
			
			  gl.glPushMatrix();
			  
			  gl.glRotated(angle1, 1, 0, 0);
			 
	  //   gl.glPopMatrix();
	     drawshape();
	     gl.glPopMatrix();
	      
	     gl.glRotatef(-angletri,1f,0,0);
	     gl.glTranslatef(0,HEIGHT/18,0);
	     
	    gl.glRotated(angle1, 0, 0, 1);     //if we allow rotation about z axis then the triangle plane will intersect with the sides of the box.
	     									//so I have disabled this roatation over here.  to see the effect just remove the comment quotes.
	     drawtriangle();
	     gl.glPopMatrix();
	     
	     
			 try {
					Thread.sleep(10);
				} catch (Exception ignore) {
				}
	      
		
	}
	
	private void drawshape() {
		angleCube=angleCube+0.1f;
		
		angle1=angle1+0.2f;
		 gl.glBegin(GL.GL_QUADS); // start of the box
		  
		  gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
	      gl.glVertex3d(-WIDTH/8,HEIGHT/8,0);
	      gl.glVertex3d(WIDTH/8,HEIGHT/8, 0);
	      gl.glVertex3d(WIDTH/8,0,200);
	      gl.glVertex3d(-WIDTH/8, 0, 200);
	      
	      gl.glColor3f(0.35f, 0.25f, 0.5f); //indigo
	      gl.glVertex3d(WIDTH/8,0,200);
	      gl.glVertex3d(-WIDTH/8, 0, 200);
	      gl.glVertex3d(-WIDTH/8,-HEIGHT/8,0);
	      gl.glVertex3d(WIDTH/8, -HEIGHT/8, 0);
	      
	      
	      gl.glColor3f(0.0f, 0.0f, 1.0f);  //Blue
	      gl.glVertex3d(-WIDTH/8,-HEIGHT/8,0);
	      gl.glVertex3d(WIDTH/8, -HEIGHT/8, 0);
	      gl.glVertex3d(WIDTH/8,0,-200);
	      gl.glVertex3d(-WIDTH/8, 0, -200);
	      
	      gl.glColor3f(1.0f, 0.0f, 0.0f); //red
	      gl.glVertex3d(WIDTH/8,0,-200);
	      gl.glVertex3d(-WIDTH/8, 0, -200);
	      gl.glVertex3d(-WIDTH/8,HEIGHT/8,0);
	      gl.glVertex3d(WIDTH/8,HEIGHT/8, 0);
	     
    gl.glEnd(); //End of Box
 	
	}

	
	private void drawtriangle(){
		angletri=angletri+0.3f;
		gl.glBegin(GL.GL_TRIANGLES); // of the triangle
	    
	    // Font-face triangle
	    gl.glColor3f(0.6f, 0.55f,0.20f); 
	    
	    
	    gl.glVertex3d(WIDTH/8+35, HEIGHT/8-50, 0);
	   
	    gl.glVertex3d(WIDTH/8+35, -HEIGHT/8+50, 0);
	 
	    gl.glVertex3d(-WIDTH/8-35, 0,0);
	    
	    gl.glEnd();//end of the triangle
	  
	}

	public void displayChanged(GLAutoDrawable drawable,boolean modeChanged,boolean deviceChanged){
		
	}
	
	public static void main(String args[])
	{
		HW3_nsingh6_BoxRot f1=new HW3_nsingh6_BoxRot();
		f1.setTitle("Box and Triangle Rotation");
		f1.setSize(WIDTH,HEIGHT);
		f1.setVisible(true);
		
	}
	

}
