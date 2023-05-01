package net.letskit.redbook.first;
import net.letskit.redbook.glskeleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;
import javax.media.opengl.GLEventListener;

public class FM17A_20030835_Jaramillo//
        extends glskeleton//
        implements //
        GLEventListener//
        ,
         KeyListener//
        ,
         MouseListener//
{
    private float spin = 0f, spinDelta = 0f;

    private static final float X = 0.525731112119133606f;
    private static final float Z = 0.850650808352039932f;
    //12 vertices
    static float vdata[][] = {
        {-X, 0.0f, Z}, {X, 0.0f, Z}, {-X, 0.0f, -Z}, {X, 0.0f, -Z},
        {0.0f, Z, X}, {0.0f, Z, -X}, {0.0f, -Z, X}, {0.0f, -Z, -X},
        {Z, X, 0.0f}, {-Z, X, 0.0f}, {Z, -X, 0.0f}, {-Z, -X, 0.0f}
    };
    //20 caras
    static int tindices[][] = {
        {0, 4, 1}, {0, 9, 4}, {9, 5, 4}, {4, 5, 8}, {4, 8, 1},
        {8, 10, 1}, {8, 3, 10}, {5, 3, 8}, {5, 2, 3}, {2, 7, 3},
        {7, 10, 3}, {7, 6, 10}, {7, 11, 6}, {11, 0, 6}, {0, 1, 6},
        {6, 1, 10}, {9, 0, 11}, {9, 11, 2}, {9, 2, 5}, {7, 2, 11}};
    public FM17A_20030835_Jaramillo() {
    }
    public static void main(String[] args) {
        //
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);// request double buffer display mode
        GLJPanel canvas = new GLJPanel(caps);
        FM17A_20030835_Jaramillo demo = new FM17A_20030835_Jaramillo();
        demo.setCanvas(canvas);
        canvas.addGLEventListener(demo);
        demo.setDefaultListeners(demo);

        demo.setDefaultAnimator(24);
        //
//        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Icosaedro");
        frame.setSize(512, 256);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
        demo.animate();
    }
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_FLAT);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDisable(GL.GL_CULL_FACE);
        gl.glPointSize(10f);
    }

    static float colors[][] = {
        {1.0f, 0.0f, 0.0f}, // Rojo
        {0.0f, 1.0f, 0.0f}, // Verde
        {0.0f, 0.0f, 1.0f}, // Azul
        {1.0f, 1.0f, 0.0f}, // Amarillo
        {1.0f, 0.0f, 1.0f}, // Magenta
        {0.0f, 1.0f, 1.0f}, // Cian
        {0.5f, 0.5f, 0.5f}, // gris
        {1.0f, 0.5f, 0.0f}, // Naranja
        {0.0f, 0.5f, 0.5f}, // Turquesa
        {0.5f, 0.0f, 0.5f}, // Púrpura
        {0.5f, 1.0f, 0.0f}, // Lima
        {1.0f, 0.0f, 0.5f}, // Rosa
        {0.0f, 0.5f, 1.0f}, // Azul claro
        {0.5f, 0.0f, 1.0f}, // Lila
        {1.0f, 0.5f, 0.5f}, // Rosa claro
        {0.5f, 1.0f, 0.5f}, // Verde claro
        {1.0f, 1.0f, 0.5f}, // Amarillo claro
        {0.5f, 0.5f, 1.0f}, // Azul pastel
        {1.0f, 0.5f, 1.0f}, // Rosa pastel
        {0.5f, 1.0f, 1.0f}, // Cian claro
    };
    public synchronized void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        //
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glPushMatrix();
        gl.glRotatef(spin, 1.0f, 0.0f, 1.0f);
       
        int i;
        gl.glBegin(gl.GL_TRIANGLES);
        for (i = 0; i < 20; i++) {
            gl.glColor3fv(colors[i % 20], 0);
            gl.glVertex3f(vdata[tindices[i][0]][0], vdata[tindices[i][0]][1], vdata[tindices[i][0]][2]);
            gl.glVertex3f(vdata[tindices[i][1]][0], vdata[tindices[i][1]][1], vdata[tindices[i][1]][2]);
            gl.glVertex3f(vdata[tindices[i][2]][0], vdata[tindices[i][2]][1], vdata[tindices[i][2]][2]);
        }
        
        
            
        gl.glEnd();

        gl.glPopMatrix();

        gl.glFlush();

        spinDisplay();

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL gl = drawable.getGL();

        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        float aspect = 0;
        if (w <= h) {
            aspect = (float) h / (float) w;
            gl.glOrtho(-1.5, 1.5, -1.5 * aspect, 1.5 * aspect, //
                    -1.5, 1.5);
        } else {
            aspect = (float) w / (float) h;
            gl.glOrtho(-1.5 * aspect, 1.5 * aspect, -1.5, 1.5, //
                    -1.5, 1.5);
        }
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }

    private void spinDisplay() {
        spin = spin + spinDelta;
        if (spin > 360f) {
            spin = spin - 360;
        }
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                super.runExit();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent key) {
    }

    public void mouseClicked(MouseEvent key) {
    }

    public void mousePressed(MouseEvent mouse) {
        switch (mouse.getButton()) {
            case MouseEvent.BUTTON1:
                spinDelta = 2f;
                break;
            case MouseEvent.BUTTON2:
            case MouseEvent.BUTTON3:
                spinDelta = 0f;
                break;
        }//

        super.refresh();
    }

    public void mouseReleased(MouseEvent mouse) {
    }

    public void mouseEntered(MouseEvent mouse) {
    }

    public void mouseExited(MouseEvent mouse) {
    }

}//
