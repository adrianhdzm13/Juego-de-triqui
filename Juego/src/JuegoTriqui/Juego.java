package JuegoTriqui;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Juego {
    //atributos

    JFrame window;
    // inicio
    JPanel panelBegin;
    JLabel background;
    JPanel panelPlay;
    JTextField player1;
    JTextField player2;
    // juego
    JButton start;
    JLabel play1;
    JLabel play2;
    JLabel p1;
    JLabel p2;
    JLabel enteros[][] = new JLabel[3][3]; // matriz de numeros que va ser logica
    int img[][] = new int[3][3]; // matriz que carga las imagenes 
    int turn = 1;
    JLabel turnPlayer;
    int flag = 0;

    //preparar la conexion a DB
    public static final String url = "jdbc:mysql://localhost:3306/triqui";
    public static final String USERNAME = "adrian";
    public static final String PASSWORD = "12345";

    PreparedStatement ps;
    ResultSet rs;
    
    
    /**
     * METODO QUE REALIZA LA CONEXION
     */
    public static Connection getConection() {

        Connection con = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(url, USERNAME, PASSWORD);
            JOptionPane.showMessageDialog(null, "Conexion exitosa");

        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }// fin metodo

    //constructor de la clase 
    public Juego() {

        window = new JFrame("JUEGO DE TRIQUI");
        window.setSize(550, 600);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//no siga ejecutando en segundo plano
        window.setResizable(false);//para no agrandar la pantalla

        panelBegin = new JPanel();
        panelBegin.setSize(window.getWidth(), window.getHeight()); //para que cambiemos el tamaño del panel cuando cambiemos de ventana
        panelBegin.setLayout(null);
        panelBegin.setVisible(true);
        //anelBegin.setBackground(Color.yellow);

        background = new JLabel();
        background.setIcon(new ImageIcon("imagenes/PNG/fondo2.png"));
        background.setSize(window.getWidth(), window.getHeight());
        background.setVisible(true);
        panelBegin.add(background, 0);//añadimos la imagen al panel de inicio

        //posicion para JTextField player1 y player 2
        player1 = new JTextField("");
        player1.setBounds(175, 220, 200, 30);
        player1.setVisible(true);
        Font fuente = new Font("Calibri", 5, 19);
        player1.setFont(fuente);
        player1.setForeground(Color.red);
        panelBegin.add(player1, 0);

        player2 = new JTextField("");
        player2.setBounds(175, 254, 200, 30);
        player2.setVisible(true);
        Font fuente1 = new Font("Calibri", 5, 19);
        player2.setFont(fuente);
        player2.setForeground(Color.red);
        panelBegin.add(player2, 0);

        //posicion para boton
        start = new JButton("Comenzar");
        start.setBounds(225, 288, 100, 30);
        start.setVisible(true);
        start.setBackground(Color.black);
        Font fuente6 = new Font("Calibri", Font.BOLD, 15);
        start.setFont(fuente6);
        start.setForeground(Color.pink);
        panelBegin.add(start, 0);

        panelPlay = new JPanel();
        panelPlay.setSize(window.getWidth(), window.getHeight()); //para que cambiemos el tamaño del panel cuando cambiemos de ventana
        panelPlay.setLayout(null);
        panelPlay.setVisible(true);
        //panelBegin.setBackground(Color.red);

        play1 = new JLabel();
        play1.setSize(200, 30);
        play1.setLocation(10, 10);
        play1.setVisible(true);
        Font fuente9 = (new Font("Calibri", Font.BOLD, 19));
        play1.setFont(fuente9);
        play1.setForeground(Color.green);//color letra
        panelPlay.add(play1, 0);

        play2 = new JLabel();
        play2.setSize(200, 30);
        play2.setLocation(window.getWidth() - (10 + play2.getWidth() + 10), 10);//pors i cambiamos el tamañod e la ventana
        play2.setVisible(true);
        Font fuente8 = (new Font("Calibri", Font.BOLD, 19));
        play2.setFont(fuente8);
        play2.setForeground(Color.green);//color letra
        panelPlay.add(play2, 0);

        //label inicio
        p1 = new JLabel();
        p1.setSize(200, 30);
        p1.setLocation(85, 220);
        p1.setVisible(true);
        p1.setText("Jugador 1 :");
        Font fuente3 = new Font("Calibri", Font.BOLD, 19);
        p1.setFont(fuente3);
        p1.setForeground(Color.pink);//color letra
        panelBegin.add(p1, 0);

        //label inicio
        p2 = new JLabel();
        p2.setSize(200, 30);
        p2.setLocation(85, 255);
        p2.setVisible(true);
        p2.setText("Jugador 2 :");
        Font fuente4 = (new Font("Calibri", Font.BOLD, 19));
        p2.setFont(fuente4);
        p2.setForeground(Color.pink);//color letra
        panelBegin.add(p2, 0);

        //label para mensaje del turno del jugador 
        turnPlayer = new JLabel();
        turnPlayer.setSize(500, 30);
        turnPlayer.setLocation(30, 500);
        turnPlayer.setVisible(true);
        Font fuente7 = (new Font("Calibri", Font.BOLD, 25));
        turnPlayer.setFont(fuente7);
        turnPlayer.setForeground(Color.green);//color letra
        panelPlay.add(turnPlayer, 0);

        //para enviarnos al juego
        start.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                System.out.println("presiono comenzar");

                //variables para capturar el JTextFlield
                String pla1, pla2;
                pla1 = player1.getText();
                pla2 = player2.getText();

                //condicionales para acceder al juego
                if (pla1.compareTo("") == 0 || pla2.compareTo("") == 0) {
                    JOptionPane.showMessageDialog(null, "campos vacios");
                } //validacion si los datos ingresados son iguales
                else if (pla1.equals(pla2)) {
                    JOptionPane.showMessageDialog(null, "NOMBRES DE LOS JUGADORES REPETIDOS.\nFAVOR UTILIZAR DIFERENTES NOMBRES");
                }
                //****************************************************************************************
                //  Ingreso de nombres de jugadores en BD
                //*****************************************************************************************
                Connection con = null;

                con = getConection();

                try {
                    ps = (PreparedStatement) con.prepareStatement("INSERT INTO juego (jugador1, jugador2, dataTime) VALUES(?,?,?)");

                    ps.setString(1, player1.getText());
                    ps.setString(2, player2.getText());
                    LocalDateTime datatime = LocalDateTime.now();
                    String fecha;
                    fecha = String.valueOf(datatime);
                    ps.setString(3,fecha);
                    
                    int resul = ps.executeUpdate();

                    //evalua si se guarda el resultado
                    if (resul > 0) {

                        JOptionPane.showMessageDialog(null, "NOMBRE DE JUGADORES GUARDADO");

                    }
                } catch (SQLException ex) {
                    //Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "ERROR: "+ex);
                }// try catch finconexion DB

                //valida  que el los nombres ingresados sean diferentes, sim son diferentes pasa a la siguiente ventana
                if (!pla1.equals(pla2)) {
                    panelBegin.setVisible(false);  //oculta el panel de inicio 
                    background.setIcon(new ImageIcon("imagenes/PNG/fondo6.png"));//cambiar imagen
                    panelPlay.add(background);//asigna un fondo nuevo

                    play1.setText("Jugador 1: " + player1.getText()); //pasamos la informacion del jugador 
                    play2.setText("Jugador 2: " + player2.getText());

                    turnPlayer.setText("Turno del judor:  " + pla1 + " con la letra O");// indicacion para el primer jugador

                    //********************************************************************************************
                    //muestra el tablero de triqui
                    for (int i = 0; i < enteros.length; i++) {
                        for (int j = 0; j < enteros.length; j++) {
                            img[i][j] = 0;
                            enteros[i][j] = new JLabel(); //damos memoria alas imagenes de la matriz
                            enteros[i][j].setIcon(new ImageIcon("imagenes/" + img[i][j] + ".png"));
                            enteros[i][j].setBounds(125 + 102 * i, 150 + 102 * j, 100, 100);
                            enteros[i][j].setVisible(true);
                            panelPlay.add(enteros[i][j], 0);

                        }
                    }

                    for (int i = 0; i < enteros.length; i++) {
                        for (int j = 0; j < enteros.length; j++) {

                            enteros[i][j].addMouseListener(new MouseAdapter() {
                                public void mouseClicked(MouseEvent e) {
                                    for (int k = 0; k < enteros.length; k++) {
                                        for (int l = 0; l < enteros.length; l++) {

                                            if (e.getSource() == enteros[k][l]) {// trae el componnete que le hice clic
                                                System.out.println(k + " " + l);
                                                //alterna los turnos
                                                if (img[k][l] == 0) {
                                                    int player = 0;
                                                    String name;
                                                    if (turn == 1) {
                                                        img[k][l] = 1;
                                                        player = 1;
                                                        name = pla1;
                                                        //obj.win(1," "+ pla1);
                                                        turnPlayer.setText("Turno del jugador;  " + pla2 + " con la letra X");
                                                        turn *= -1;
                                                    }//fin if turn
                                                    else {
                                                        img[k][l] = 2;
                                                        player = 2;
                                                        name = pla2;
                                                        //obj.win(2," "+ pla2);
                                                        turnPlayer.setText("Turno del jugador;  " + pla1 + " con la letra O");

                                                        turn *= -1;
                                                    }// fin else
                                                    enteros[k][l].setIcon(new ImageIcon("imagenes/" + img[k][l] + ".png"));
                                                    //ganador diagonal principal
                                                    if (img[0][0] == player && img[1][1] == player && img[2][2] == player) {
                                                        flag = 1;
                                                    }
                                                    //ganador diagonal secundaria
                                                    if (img[2][0] == player && img[1][1] == player && img[0][2] == player) {
                                                        flag = 1;
                                                    }
                                                    //ganador de arriba hacia abajo. primera columna
                                                    if (img[0][0] == player && img[0][1] == player && img[0][2] == player) {
                                                        flag = 1;
                                                    }
                                                    //ganador de arriba hacia abajo. segunda columna
                                                    if (img[1][0] == player && img[1][1] == player && img[1][2] == player) {
                                                        flag = 1;
                                                    }
                                                    //ganador de arriba hacia abajo. tercera columna
                                                    if (img[2][0] == player && img[2][1] == player && img[2][2] == player) {
                                                        flag = 1;
                                                    }
                                                    //ganador de la primera fila
                                                    if (img[0][0] == player && img[1][0] == player && img[2][0] == player) {
                                                        flag = 1;
                                                    }
                                                    //ganador de la segunda fila
                                                    if (img[0][1] == player && img[1][1] == player && img[2][1] == player) {
                                                        flag = 1;
                                                    }
                                                    //ganador de la tercera fila
                                                    if (img[0][2] == player && img[1][2] == player && img[2][2] == player) {
                                                        flag = 1;
                                                    }

                                                    if (flag == 1) {
                                                        JOptionPane.showMessageDialog(window, name + "\n*** FELICIDADES HAS GANADO *** ");
                                                        flag = 0;
                                                        turn = 1;
                                                        //para limpiar el tablero
                                                        for (int m = 0; m < enteros.length; m++) {
                                                            for (int n = 0; n < enteros.length; n++) {

                                                                img[m][n] = 0;
                                                                enteros[m][n].setIcon(new ImageIcon("imagenes/" + img[m][n] + ".png"));
                                                            }
                                                        }
                                                    }// fin if ganador

                                                    int contador = 0;
                                                    for (int m = 0; m < enteros.length; m++) {
                                                        for (int n = 0; n < enteros.length; n++) {
                                                            if (img[m][n] != 0) {
                                                                contador++;
                                                            }
                                                        }
                                                    }

                                                    if (contador == 9) {
                                                        JOptionPane.showMessageDialog(window, "***  NO HAY GANADOR  ***");
                                                        contador = 0;
                                                        flag = 0;
                                                        turn = 1;
                                                        //para limpiar el tablero
                                                        for (int m = 0; m < enteros.length; m++) {
                                                            for (int n = 0; n < enteros.length; n++) {

                                                                img[m][n] = 0;
                                                                enteros[m][n].setIcon(new ImageIcon("imagenes/" + img[m][n] + ".png"));
                                                            }
                                                        }
                                                    }

                                                }// fin if img[k][l] == 0

                                            }
                                        }//fin for l

                                    }//fin for k
                                }// fin evento del clic a cada una de las imagenes
                            });
                        }//fin for j
                    }// fin for i

                    window.add(panelPlay);//cambia la foto//agreagmos le panel a la ventana

                }// fin if 

            }// fin mouseClicked
        });//fin metodo

        window.add(panelBegin);//agreagmos le panel a la ventana
        window.setVisible(true);

    }// fin constructor

    /**
     * Metodo que realiza la insercion de datos en la DB
     *
     */
    public void startActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {

        Connection con = null;
        try {

            con = getConection();

            ps = (PreparedStatement) con.prepareStatement("INSERT INTO juego (jugador1, jugador2) VALUES(?,?)");

            ps.setString(1, player1.getText());
            ps.setString(2, player2.getText());

            ps.executeUpdate();
            int resul = ps.executeUpdate();

            //evalua si se guarda el resultado
            if (resul > 0) {

                JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " ERROR: " + e);

        }
    }//fin metodo

}//fin clase

