package view.components;

import view.global.GlobalVariables;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagemCarro extends ImageIcon {
    
    public ImagemCarro(ImageIcon imagemFundo, int r, int g, int b) {
        desenharCarro(imagemFundo, r, g, b);
    }
    
    private void desenharCarro(ImageIcon imagemFundo, int r, int g, int b) {
        BufferedImage bufferedImage = new BufferedImage(imagemFundo.getIconWidth(), imagemFundo.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2D = bufferedImage.createGraphics();
        g2D.drawImage(imagemFundo.getImage(), 0, 0, null);
        //Desenha parte de cima
        g2D.setColor(new Color(r, g, b));
        g2D.fillRoundRect((int)(GlobalVariables.LADO_QUADRADO*0.1), 0, (int)(GlobalVariables.LADO_QUADRADO*0.8), (int)(GlobalVariables.LADO_QUADRADO*0.7), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5));
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(2));
        g2D.drawRoundRect((int)(GlobalVariables.LADO_QUADRADO*0.1), 0, (int)(GlobalVariables.LADO_QUADRADO*0.8), (int)(GlobalVariables.LADO_QUADRADO*0.7), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5));
    
        //Desenha vidro
        g2D.setColor(Color.CYAN);
        g2D.fillRoundRect((int)(GlobalVariables.LADO_QUADRADO*0.2), (int)(GlobalVariables.LADO_QUADRADO*0.1), (int)(GlobalVariables.LADO_QUADRADO*0.6), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5));
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(2));
        g2D.drawRoundRect((int)(GlobalVariables.LADO_QUADRADO*0.2), (int)(GlobalVariables.LADO_QUADRADO*0.1), (int)(GlobalVariables.LADO_QUADRADO*0.6), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5));
    
        //Desenha roda esquerda
        g2D.setStroke(new BasicStroke(0));
        g2D.fillRoundRect((int)(GlobalVariables.LADO_QUADRADO*0.1), (int)(GlobalVariables.LADO_QUADRADO*0.6), (int)(GlobalVariables.LADO_QUADRADO*0.3), (int)(GlobalVariables.LADO_QUADRADO*0.4), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5));
    
        //Desenha roda direita
        g2D.fillRoundRect((int)(GlobalVariables.LADO_QUADRADO*0.6), (int)(GlobalVariables.LADO_QUADRADO*0.6), (int)(GlobalVariables.LADO_QUADRADO*0.3), (int)(GlobalVariables.LADO_QUADRADO*0.4), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5));
       
        //Desenha parte do meio
        g2D.setColor(new Color(r, g, b));
        g2D.fillRoundRect(0, (int)(GlobalVariables.LADO_QUADRADO*0.3), GlobalVariables.LADO_QUADRADO, (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5));
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(2));
        g2D.drawRoundRect(0, (int)(GlobalVariables.LADO_QUADRADO*0.3), GlobalVariables.LADO_QUADRADO, (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5), (int)(GlobalVariables.LADO_QUADRADO*0.5));
    
        //Desenha farol esquerdo
        g2D.setColor(Color.YELLOW);
        g2D.fillOval((int)(GlobalVariables.LADO_QUADRADO*0.15), (int)(GlobalVariables.LADO_QUADRADO*0.4), (int)(GlobalVariables.LADO_QUADRADO*0.25), (int)(GlobalVariables.LADO_QUADRADO*0.25));
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(1));
        g2D.drawOval((int)(GlobalVariables.LADO_QUADRADO*0.15), (int)(GlobalVariables.LADO_QUADRADO*0.4), (int)(GlobalVariables.LADO_QUADRADO*0.25), (int)(GlobalVariables.LADO_QUADRADO*0.25));
    
        //Desenha farol direito
        g2D.setColor(Color.YELLOW);
        g2D.fillOval((int)(GlobalVariables.LADO_QUADRADO*0.65), (int)(GlobalVariables.LADO_QUADRADO*0.4), (int)(GlobalVariables.LADO_QUADRADO*0.25), (int)(GlobalVariables.LADO_QUADRADO*0.25));
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(1));
        g2D.drawOval((int)(GlobalVariables.LADO_QUADRADO*0.65), (int)(GlobalVariables.LADO_QUADRADO*0.4), (int)(GlobalVariables.LADO_QUADRADO*0.25), (int)(GlobalVariables.LADO_QUADRADO*0.25));
        
        g2D.dispose();
        this.setImage(bufferedImage);
    }
}
