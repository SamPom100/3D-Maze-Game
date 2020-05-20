import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable{
    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public ArrayList<Texture> textures;
    public Player Player;
    public Processor screen;

    public static int[][] map = 
        {
            {1,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {6,0,0,0,0,0,0,0,0,10,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,10,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,10,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,10,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,10,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,10,10,10,0,10,10,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

    public static int[][] map2 = 
        {
            {5,6,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
            {6,0,0,0,9,9,0,0,0,0,0,0,0,0,0,5},
            {5,0,9,0,0,9,0,9,9,9,9,0,0,0,0,5},
            {5,9,0,9,0,9,0,9,9,0,0,0,0,0,0,5},
            {5,0,0,0,0,9,0,9,9,0,9,0,0,0,0,5},
            {5,0,9,0,9,0,0,9,0,9,9,9,9,9,0,5},
            {5,0,9,9,9,9,9,0,9,0,9,0,0,0,0,5},
            {5,0,0,0,9,0,0,9,0,0,0,0,9,0,0,5},
            {5,0,0,0,9,0,0,9,0,9,9,0,9,9,0,5},
            {5,0,9,0,9,0,0,9,0,9,9,0,0,9,0,5},
            {5,0,9,9,9,0,0,9,0,0,0,0,0,9,0,5},
            {5,0,0,0,9,0,0,9,0,9,0,9,0,0,0,5},
            {5,0,9,0,9,9,9,9,0,9,0,9,9,9,0,5},
            {5,0,0,0,0,0,0,0,0,9,0,0,0,0,0,5},
            {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5}
        };

    public static int[][] map3 = 
        {
            {2,6,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {6,0,8,0,0,0,0,8,0,0,0,0,0,0,0,2},
            {2,0,8,0,8,8,0,8,0,8,8,8,0,0,0,2},
            {2,0,8,8,8,8,0,8,0,0,0,8,0,0,0,2},
            {2,0,0,0,8,9,0,8,0,8,0,8,8,8,0,2},
            {2,8,8,0,8,8,0,8,0,8,0,0,0,8,0,2},
            {2,0,0,0,8,8,0,8,0,8,0,8,0,0,0,2},
            {2,0,8,8,8,8,0,8,8,8,0,8,0,8,0,2},
            {2,0,8,0,0,0,0,0,0,8,8,8,0,0,0,2},
            {2,0,8,0,8,8,8,8,0,8,0,0,0,8,0,2},
            {2,0,8,0,0,0,0,8,0,8,0,8,8,8,0,2},
            {2,0,8,8,8,8,0,8,0,8,0,0,0,0,0,2},
            {2,0,0,0,0,8,0,8,0,8,8,8,8,8,0,2},
            {2,0,8,8,0,0,0,8,0,0,0,0,0,0,0,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}
        };   

    public static int[][] map4 = 
        {
            {4,6,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            {6,0,0,7,0,0,0,0,0,0,0,0,0,0,0,4},
            {4,0,0,7,0,7,7,7,0,7,7,7,0,0,0,4},
            {4,0,0,7,0,0,0,7,0,0,0,7,0,0,0,4},
            {4,7,0,7,0,7,0,7,7,7,0,7,0,7,7,4},
            {4,0,0,7,0,7,0,7,0,0,0,7,0,0,0,4},
            {4,0,0,7,0,7,0,7,0,7,0,7,7,7,0,4},
            {4,0,7,7,0,7,0,7,0,0,0,7,0,0,0,4},
            {4,0,0,7,0,7,0,7,7,7,0,7,0,7,7,4},
            {4,0,0,7,0,7,0,0,0,0,0,7,0,0,0,4},
            {4,7,0,7,0,0,7,7,7,7,7,7,7,7,0,4},
            {4,0,0,7,7,0,7,0,0,0,0,7,0,0,0,4},
            {4,0,7,7,7,0,7,0,7,7,7,7,0,7,7,4},
            {4,0,0,0,0,0,7,0,0,0,0,0,0,0,0,4},
            {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
        };
        
    public static int[][] map5 = 
        {
            {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
            {8,0,0,0,0,0,0,0,0,0,11,0,0,0,0,2},
            {8,0,11,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {8,0,0,0,0,0,12,0,0,0,0,0,0,0,0,2},
            {8,0,0,0,0,0,0,0,0,0,0,0,0,0,12,2},
            {8,12,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {8,0,0,0,0,0,0,0,11,0,0,0,0,0,0,2},
            {8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {8,0,0,0,0,0,0,0,0,12,0,0,0,0,0,2},
            {8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {8,0,0,11,0,0,0,0,0,0,0,0,0,0,0,2},
            {8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {8,0,0,0,0,12,0,0,0,0,0,0,11,0,0,2},
            {8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8}
        };

    public Game() {
        thread = new Thread(this);
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //connects image data to pixels
        textures = new ArrayList<Texture>();
        textures.add(Texture.city); //1
        textures.add(Texture.sea); //2
        textures.add(Texture.brick1); //3
        textures.add(Texture.brick2); //4
        textures.add(Texture.brick3); //5
        textures.add(Texture.goal); //6
        textures.add(Texture.diamond); //7
        textures.add(Texture.iron); //8
        textures.add(Texture.dirt); //9
        textures.add(Texture.welcome); //10
        textures.add(Texture.win1); //11
        textures.add(Texture.win2); //12
        Player = new Player(1.5, 14, 1, 0, 0, -.66);
        screen = new Processor(map, mapWidth, mapHeight, textures, 640, 480);
        addKeyListener(Player);
        setSize(640, 480);
        setResizable(false);
        setTitle("Mazes in Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }

    private synchronized void start() { //starts in sync
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try 
        {
            thread.join();
        } 

        catch(InterruptedException e) 
        {
            e.printStackTrace();
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy(); //smoother screen updates using BufferStrategy
        if(bs == null) 
        {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null); //drawing graphics object to screen
        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; //update screen every 1/60th second
        double delta = 0;
        requestFocus();
        while(running) 
        {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)
            {
                if(Player.changeMap2)
                {
                    map = map2;
                }
                if(Player.changeMap3)
                {
                    map = map3;
                }
                if(Player.changeMap4)
                {
                    map = map4;
                }
                if(Player.changeMap5)
                {
                    map = map5;
                }
                screen.update(Player, pixels);
                Player.update(map);
                delta--;
            }
            render(); //display to screen
        }
    }

    public static void main(String [] args) {
        Game game = new Game();
    }
}

class Texture 
{
    public int[] pixels; //hold data in image
    private String loc;
    public final int SIZE; //always 64 x 64 for square
    
    
    
    public Texture(String location, int size) 
    {
        loc = location;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }
    
    private void load() 
    {
        try 
        {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } 
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public static Texture city = new Texture("Texture//c.png", 64);
    public static Texture brick1 = new Texture("Texture//brick1.png", 64);
    public static Texture brick2 = new Texture("Texture//brick2.png", 64);
    public static Texture brick3 = new Texture("Texture//brick3.png", 64);
    public static Texture sea = new Texture("Texture//b2.png",64);
    public static Texture goal = new Texture("Texture//go.png",64);
    public static Texture diamond = new Texture("Texture//diamond.png",64);
    public static Texture dirt = new Texture("Texture//dirt.png",64);
    public static Texture ore = new Texture("Texture//dore.png",64);
    public static Texture iron = new Texture("Texture//iron.png",64);
    public static Texture welcome = new Texture("Texture//welcome.png",64);
    public static Texture win1 = new Texture("Texture//win1.png",64);
    public static Texture win2 = new Texture("Texture//win2.png",64);
}

class Player implements KeyListener{
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double movementSpeed = .08;
    public final double rotateSpeed = .045;
    public static boolean changeMap2 = false;
    public static boolean changeMap3 = false;
    public static boolean changeMap4 = false;
    public static boolean changeMap5 = false;
    
    public Player(double x, double y, double xd, double yd, double xp, double yp) {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
    }

    public void keyPressed(KeyEvent key) 
    {
        if((key.getKeyCode() == KeyEvent.VK_LEFT))
            left = true;
            
        if((key.getKeyCode() == KeyEvent.VK_RIGHT))
            right = true;
            
        if((key.getKeyCode() == KeyEvent.VK_UP))
            forward = true;
            
        if((key.getKeyCode() == KeyEvent.VK_DOWN))
            back = true;

        if((key.getKeyCode() == KeyEvent.VK_F5))
        {
            resetPos();
        }

        if((key.getKeyCode() == KeyEvent.VK_F6))
        {
            nextMap();
        }

        if((key.getKeyCode() == KeyEvent.VK_F1))
        {
            System.out.println(xPos +"-"+ yPos);
        }
    }

    public void keyReleased(KeyEvent key) 
    {
        if((key.getKeyCode() == KeyEvent.VK_LEFT))
            left = false;
        if((key.getKeyCode() == KeyEvent.VK_RIGHT))
            right = false;
        if((key.getKeyCode() == KeyEvent.VK_UP))
            forward = false;
        if((key.getKeyCode() == KeyEvent.VK_DOWN))
            back = false;
    }

    public void update(int[][] map) 
    {
        if(( xPos > 1.013765758832589 && xPos < 1.724263794075187 ) 
        &&( yPos > 1.013765758832589 && yPos < 1.724263794075187 ))
        {
            nextMap();
            resetPos();
        }

        if(forward) 
        {
            if(map[(int)(xPos + xDir * movementSpeed)][(int)yPos] == 0) 
            {
                xPos+=xDir*movementSpeed;
            }

            if(map[(int)xPos][(int)(yPos + yDir * movementSpeed)] ==0)
                yPos+=yDir*movementSpeed;
        }

        if(back) 
        {
            if(map[(int)(xPos - xDir * movementSpeed)][(int)yPos] == 0)
                xPos-=xDir*movementSpeed;

            if(map[(int)xPos][(int)(yPos - yDir * movementSpeed)]==0)
                yPos-=yDir*movementSpeed;
        }

        if(right) 
        {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(-rotateSpeed) - yDir*Math.sin(-rotateSpeed);
            yDir=oldxDir*Math.sin(-rotateSpeed) + yDir*Math.cos(-rotateSpeed);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-rotateSpeed) - yPlane*Math.sin(-rotateSpeed);
            yPlane=oldxPlane*Math.sin(-rotateSpeed) + yPlane*Math.cos(-rotateSpeed);
        }

        if(left) 
        {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(rotateSpeed) - yDir*Math.sin(rotateSpeed);
            yDir=oldxDir*Math.sin(rotateSpeed) + yDir*Math.cos(rotateSpeed);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(rotateSpeed) - yPlane*Math.sin(rotateSpeed);
            yPlane=oldxPlane*Math.sin(rotateSpeed) + yPlane*Math.cos(rotateSpeed);
        }
    }

    public void keyTyped(KeyEvent e) 
    {
        //difference between keyTyped and keyPressed?
    }

    public void resetPos()
    {
        xPos = 1.5;
        yPos = 14;
        xDir = 1;
        yDir = 0;
        xPlane = 0;
        yPlane = -.66;
    }
    
    public void nextMap()
    {
        if(changeMap4)
            changeMap5 = true;
        if(changeMap3)
            changeMap4 = true;
        if(changeMap2)
            changeMap3 = true;
        changeMap2 = true;    
    }
}

class Processor 
{
    public int[][] map;
    public int mapWidth, mapHeight, width, height;
    public ArrayList<Texture> textures;

    public Processor(int[][] m, int mapW, int mapH, ArrayList<Texture> tex, int w, int h) 
    {
        map = m;
        mapWidth = mapW;
        mapHeight = mapH;
        textures = tex;
        width = w;
        height = h;
    }

    public int[] update(Player Player, int[] pixels) 
    {
        if(Player.changeMap2)
        {
            map = Game.map2;
        }
        if(Player.changeMap3)
        {
            map = Game.map3;
        }
        if(Player.changeMap4)
        {
            map = Game.map4;
        }
        if(Player.changeMap5)
        {
            map = Game.map5;
        }
        
        
        for(int n=0; n<pixels.length/2; n++) 
        {
            if(pixels[n] != Color.DARK_GRAY.getRGB()) 
                pixels[n] = Color.DARK_GRAY.getRGB();
        }
        
        for(int i=pixels.length/2; i<pixels.length; i++)
        {
            if(pixels[i] != Color.gray.getRGB()) 
                pixels[i] = Color.gray.getRGB();
        }

        for(int x=0; x<width; x=x+1) {
            ////////////////////////////////////////////////////////////////Directions///////////////////////////////////////////
            double PlayerX = 2 * x / (double)(width) -1;
            double rayDirX = Player.xDir + Player.xPlane * PlayerX;
            double rayDirY = Player.yDir + Player.yPlane * PlayerX;
            ////////////////////////////////////////////////////////////////Map position/////////////////////////////////////////
            int mapX = (int)Player.xPos;
            int mapY = (int)Player.yPos;
            double sideDistX; //current pos to next x/y
            double sideDistY;
            double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX)); //side to side of map
            double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
            double perpWallDist;
            int stepX, stepY; //step incrument
            boolean hit = false;
            int side=0;
            
            /////////////////////////////////////////////////////////////////Step/wall distance calcs///////////////////////////////
            if (rayDirX < 0)
            {
                stepX = -1; sideDistX = (Player.xPos - mapX) * deltaDistX;
            }
            else
            {
                stepX = 1; sideDistX = (mapX + 1.0 - Player.xPos) * deltaDistX;
            }
            
            if (rayDirY < 0)
            {
                stepY = -1; sideDistY = (Player.yPos - mapY) * deltaDistY;
            }
            else
            {
                stepY = 1; sideDistY = (mapY + 1.0 - Player.yPos) * deltaDistY;
            }
            
            while(!hit) //ensure ray hits wall
            {
                if (sideDistX < sideDistY) //next box
                {
                    sideDistX += deltaDistX; mapX += stepX; side = 0;
                }
                else
                {
                    sideDistY += deltaDistY; mapY += stepY; side = 1;
                }
                if(map[mapX][mapY] > 0) //if ray hits wall then update hit
                    hit = true; 
            }
            
            if(side==0) //distance to the point of impact
                perpWallDist = Math.abs((mapX - Player.xPos + (1 - stepX) / 2) / rayDirX);
            else
                perpWallDist = Math.abs((mapY - Player.yPos + (1 - stepY) / 2) / rayDirY);
                
            int lineHeight;
            
            if(perpWallDist > 0) 
                lineHeight = Math.abs((int)(height / perpWallDist)); //calculate the height of the wall based on the distance from the Player
            else 
                lineHeight = height;
            
            
            
            ///////////////////////////////////////////////////////////////////////////////Textures and striping/////////////////////////////
            int drawStart = -lineHeight/2+ height/2; // lowest and highest pixel in current stripe
            
            if(drawStart < 0)
                drawStart = 0;
                
            int drawEnd = lineHeight/2 + height/2;
            
            if(drawEnd >= height) 
                drawEnd = height - 1;
                
            int texNum = map[mapX][mapY] - 1; //attach texture
            double wallX;
            
            if(side==1) //y
            {
                wallX = (Player.xPos + ((mapY - Player.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
            } 
            else //x
            {
                wallX = (Player.yPos + ((mapX - Player.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
            }
            
            wallX-=Math.floor(wallX); //texture x
            int texX = (int)(wallX * (textures.get(texNum).SIZE));
            
            if(side == 0 && rayDirX > 0) 
                texX = textures.get(texNum).SIZE - texX - 1;
                
            if(side == 1 && rayDirY < 0) 
                texX = textures.get(texNum).SIZE - texX - 1;
                
            for(int y=drawStart; y<drawEnd; y++) //texture y
            {
                int texY = (((y*2 - height + lineHeight) << 6) / lineHeight) / 2;
                int color;
                if(side==0) 
                    color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)];
                else 
                    color = (textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)]>>1) & 8355711;//Make y sides darker
                pixels[x + y*(width)] = color;
            }
        }
        return pixels;
    }
}