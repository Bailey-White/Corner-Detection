import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

// Main class
public class CornerDetection extends Frame implements ActionListener {
	BufferedImage input;
	int width, height;
	double sensitivity=.1;
	int threshold=20;
	ImageCanvas source, target;
	CheckboxGroup metrics = new CheckboxGroup();
        
        int convolveX[] = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
	int convolveY[] = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
	int templateSize = 3;
	// Constructor
	public CornerDetection(String name) {
		super("Corner Detection");
		// load image
		try {
			input = ImageIO.read(new File(name));
		}
		catch ( Exception ex ) {
			ex.printStackTrace();
		}
		width = input.getWidth();
		height = input.getHeight();
		// prepare the panel for image canvas.
		Panel main = new Panel();
		source = new ImageCanvas(input);
		target = new ImageCanvas(width, height);
		main.setLayout(new GridLayout(1, 2, 10, 10));
		main.add(source);
		main.add(target);
		// prepare the panel for buttons.
		Panel controls = new Panel();
		Button button = new Button("Derivatives");
		button.addActionListener(this);
		controls.add(button);
		// Use a slider to change sensitivity
		JLabel label1 = new JLabel("sensitivity=" + sensitivity);
		controls.add(label1);
		JSlider slider1 = new JSlider(1, 25, (int)(sensitivity*100));
		slider1.setPreferredSize(new Dimension(50, 20));
		controls.add(slider1);
		slider1.addChangeListener(changeEvent -> {
			sensitivity = slider1.getValue() / 100.0;
			label1.setText("sensitivity=" + (int)(sensitivity*100)/100.0);
		});
		button = new Button("Corner Response");
		button.addActionListener(this);
		controls.add(button);
		JLabel label2 = new JLabel("threshold=" + threshold);
		controls.add(label2);
		JSlider slider2 = new JSlider(0, 100, threshold);
		slider2.setPreferredSize(new Dimension(50, 20));
		controls.add(slider2);
		slider2.addChangeListener(changeEvent -> {
			threshold = slider2.getValue();
			label2.setText("threshold=" + threshold);
		});
		button = new Button("Thresholding");
		button.addActionListener(this);
		controls.add(button);
		button = new Button("Non-max Suppression");
		button.addActionListener(this);
		controls.add(button);
		button = new Button("Display Corners");
		button.addActionListener(this);
		controls.add(button);
		// add two panels
		add("Center", main);
		add("South", controls);
		addWindowListener(new ExitListener());
		setSize(Math.max(width*2+100,850), height+110);
		setVisible(true);
	}
	class ExitListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
	// Action listener for button click events
	public void actionPerformed(ActionEvent e) {
		// generate Moravec corner detection result
		if ( ((Button)e.getSource()).getLabel().equals("Derivatives") ){
                    derivatives();
                }	
                if ( ((Button)e.getSource()).getLabel().equals("Derivatives") ){
                    
                }
                if ( ((Button)e.getSource()).getLabel().equals("Derivatives") ){
                    
                }
                if ( ((Button)e.getSource()).getLabel().equals("Derivatives") ){
                    
                }
                    
                
	}
	public static void main(String[] args) {
		new CornerDetection(args.length==1 ? args[0] : "signal_hill.png");
	}

	// moravec implementation
	void derivatives() {
		int l, t, r, b, dx, dy;
		Color clr1, clr2;
		int gray1, gray2;

		for ( int q=0 ; q<height ; q++ ) {
			t = q==0 ? q : q-1;
			b = q==height-1 ? q : q+1;
			for ( int p=0 ; p<width ; p++ ) {
				l = p==0 ? p : p-1;
				r = p==width-1 ? p : p+1;
				clr1 = new Color(source.image.getRGB(l,q));
				clr2 = new Color(source.image.getRGB(r,q));
				gray1 = clr1.getRed() + clr1.getGreen() + clr1.getBlue();
				gray2 = clr2.getRed() + clr2.getGreen() + clr2.getBlue();
				dx = (gray2 - gray1) / 3;
				clr1 = new Color(source.image.getRGB(p,t));
				clr2 = new Color(source.image.getRGB(p,b));
				gray1 = clr1.getRed() + clr1.getGreen() + clr1.getBlue();
				gray2 = clr2.getRed() + clr2.getGreen() + clr2.getBlue();
				dy = (gray2 - gray1) / 3;
				dx = Math.max(-128, Math.min(dx, 127));
				dy = Math.max(-128, Math.min(dy, 127));
				target.image.setRGB(p, q, new Color(dx+128, dy+128, 128).getRGB());
			}
		}
		target.repaint();
	}
        public int[] convoluteImages
	{
			double derX[] = new double[width*height];
			double derY[] = new double[width*height];			
			double derXy[] = new double[width*height];
			double valx, valy;
                        int progress = 0;
		
			for(int x = templateSize / 2; x < width - (templateSize / 2); x++) {
				progress++;
				for(int y= templateSize / 2; y < height- (templateSize / 2); y++) {
					valx = 0;
					valy = 0;					
					for(int x1 = 0; x1 < templateSize; x1++) {
						for(int y1 = 0; y1 < templateSize; y1++) {
							int pos = (y1 * templateSize + x1);
							int imPos = (x + (x1 - (templateSize / 2))) + ((y + (y1 - (templateSize / 2))) * width);
							
							valx +=((input[imPos]&0xff) * convolveX[pos]);
							valy +=((input[imPos]&0xff) * convolveY[pos]);
						}
					}
					derX[x + (y * width)] = valx * valx;
					derY[x + (y * width)] = valy * valy;
					derXy[x + (y * width)] = valx * valy;
				}
			}
	}

		// Creates Gaussian kernel and applies gaussian filter to img
    private BufferedImage gaussian(BufferedImage img)
    {
        int w = 2;
        
        BufferedImage tempImg =  new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB); //creates a temporary image holding values of passes
        float k[] = buildKernel();
        
        for (int y = 0; y < height; y++) //looping through the image whilst applying kernels 
        {
            for (int x = 0; x < width; x++)
            {
                int j = kernelBoundsCheck(x,w); 
                float red = 0, green = 0, blue = 0;
                
                for (int i = 0; i < k.length; i++)
                {
                    //color value at pixel
                    Color clr = new Color(img.getRGB(j + i - w, y));
                    red += clr.getRed() * k[i];
                    green += clr.getGreen() * k[i];
                    blue += clr.getBlue() * k[i];
                }
                
                int rgb = ((int)red << 16) | ((int)green << 8) | (int)blue; //  rgb color from each channel
                
                tempImg.setRGB(y, x, rgb); //setting the color at each coordinate
            }
        }
        return tempImg;
    }

    private int kernelBoundsCheck(int x, int y) //checks if the index will make the kernel out of bounds
    {
    	int w = 2; 
    	
    	if (x < y)
    	{
    		return y;
    	}
    	if (x >= width - y)
    	{
    		return width - (w + 1);
    	}
    	else
    	{
    		return x;
    	}
    }
    
    private float[] buildKernel() //creates 5x5 Gaussian filter
    {
        //creates an array to hold the kernel values
        int w = 2; //width of kernels from pixel
        float kernel[] = new float[2*w+1];

        
        for (int i = 0; i < kernel.length; i++)
        {
            kernel[i] = gaussianFunc(i-2);
        }

        return normalizeKernel(kernel); //normalizes & returns kernel array
    }

    private float[] normalizeKernel(float[] array) //normalizes kernel values by dividing each by sum of kernel values
    {
        float kernel[] = new float[array.length];
        float sum = 0;
        for (float i : array) //adding up the sum
        {
            sum += i;
        }
        for (int i = 0; i < kernel.length; i++) //dividing each value by sum
        {
            kernel[i] = array[i] / sum;
        }

        return kernel;
    }

    private float gaussianFunc(float x) //gets the value of Gaussian function
    {
        float sigma = Float.parseFloat(textSigma.getText()); //reads input from textbox
        //calculates Gaussian function
        double fx = 1 / (Math.sqrt(2 * Math.PI) * sigma);
        double exp = (-x * x) / (2.0 * Math.pow(sigma, 2));
        double hx = Math.pow(Math.E, exp);
        return (float)(fx * hx);
    }

    public double[] nonMaxSup
    {
    		double derX[] = new double[width*height];
			double derY[] = new double[width*height];			
			double pixArr[] = new double[width*height];
			
			double valx, valy;
		
			for(int x = templateSize / 2; x < width - (templateSize / 2); x++) 
			{
				for(int y= templateSize / 2; y < height- (templateSize / 2); y++) 
				{
					valx = 0;
					valy = 0;					
					for(int x1 = 0; x1 < templateSize; x1++) 
					{
						for(int y1 = 0; y1 < templateSize; y1++) 
						{
							int pos = (y1 * templateSize + x1);
							int imPos = (x + (x1 - (templateSize / 2))) + ((y + (y1 - (templateSize / 2))) * width);
							
							valx +=((input[imPos]) * convolveX[pos]);
							valy +=((input[imPos]) * convolveY[pos]);
						}
					}
					derX[x + (y * width)] = valx;
					derY[x + (y * width)] = valy;
					pixArr[x + (y * width)] = Math.sqrt((valx * valx) + (valy * valy));
				}
			}

			for(int x = 1; x < width - 1; x++) 
			{
				for(int y = 1 ; y < height - 1; y++) 
				{
					int dx, dy;
					
					if(derX[x + (y * width)] > 0) dx = 1;
					else dx = -1;

					if(derY[x + (y * width)] > 0) dy = 1;
					else dy = -1;					
						
					double a1, a2, b1, b2, A, B, point, val;
					if(Math.abs(derX[x + (y * width)]) > Math.abs(derY[x + (y * width)]))
					{
						a1 = pixArr[(x+dx) + ((y) * width)];
						a2 = pixArr[(x+dx) + ((y-dy) * width)];
						b1 = pixArr[(x-dx) + ((y) * width)];
						b2 = pixArr[(x-dx) + ((y+dy) * width)];
						A = (Math.abs(derX[x + (y * width)]) - Math.abs(derY[x + (y * width)]))*a1 + Math.abs(derY[x + (y * width)])*a2;
						B = (Math.abs(derX[x + (y * width)]) - Math.abs(derY[x + (y * width)]))*b1 + Math.abs(derY[x + (y * width)])*b2;
						point = pixArr[x + (y * width)] * Math.abs(derX[x + (y * width)]);
						if(point >= A && point > B) 
						{
							val = Math.abs(derX[x + (y * width)]);
							output[x + (y * width)] = val;
						}
						else 
						{
							val = 0;
							output[x + (y * width)] = val;
						}
					}
					else 
					{
						a1 = pixArr[(x) + ((y-dy) * width)];
						a2 = pixArr[(x+dx) + ((y-dy) * width)];
						b1 = pixArr[(x) + ((y+dy) * width)];
						b2 = pixArr[(x-dx) + ((y+dy) * width)];						
						A = (Math.abs(derY[x + (y * width)]) - Math.abs(derX[x + (y * width)]))*a1 + Math.abs(derX[x + (y * width)])*a2;
						B = (Math.abs(derY[x + (y * width)]) - Math.abs(derX[x + (y * width)]))*b1 + Math.abs(derX[x + (y * width)])*b2;
						point = pixArr[x + (y * width)] * Math.abs(derY[x + (y * width)]);
						if(point >= A && point > B) 
						{
							val = Math.abs(derY[x + (y * width)]);
							output[x + (y * width)] = val;
						}
						else 
						{
							val = 0;
							output[x + (y * width)] = val;
						}							
					}
				}
			}
			
			return output;
		}

	public void cornerResponse
	{
		double cornerArr[] = new double[width * height];
		double val = 0;
		double max = 0;
		double maxA = 0;
		double min = 100;
		double A, B, C;
		for(int x = 0; x < width; x++) {
			progress++;
			for(int y = 0 ; y < height; y++) {
				A = (diffx[y * width + x]);
				B = (diffy[y * width + x]);
				C = (diffxy[y * width + x]);
				val = ((A * B - (C * C)) - (sensitivity*((A + B)*(A + B))));
				
				if(val > threshold) cornerArr[y * width + x] = val;
				else cornerArr[y * width + x] = 0;
	}
    }
}
}
