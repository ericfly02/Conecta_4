package edu.epsevg.prop.lab.c4;

/**
 * MINMAX
 * "Minmax mes heuristica"
 * @author eric
 */
public class MinMax implements Jugador, IAuto{
  private String nom;
  private int profunditat;

  /**
   * Constructor de la classe MinMax
   * @param profunditat profunditat de minimax ( comptant torns de player 1 i player 2 separadament )
   */
  public MinMax(int profunditat)
  {
    nom = "SUDO";
    this.profunditat = profunditat;
  }

  /**
   * La funció té un comptador, per a saber el nombre de fitxes que hi ha seguides de manera vertical des de la posició passada per argument fins al final del tauler, si troba una fitxa de diferent color para. Seguidament, mira si el nombre de fitxes que ha trobat és major o igual que el grup a avaluar, és a dir, que hi ha 4,3 o 2 fitxes seguides. Si és major o igual retorna 1, 0 altrament.
   * @param t Tauler actual de joc
   * @param columna columna a explorar
   * @param fila fila a explorar
   * @param color Color de la peça que possarà
   * @param grup numero de fitxes del mateix jugador seguides
   * @return Retorna 1 si hi han mes fitxes que el numero de fitxes seguides actualmetn. Retorna 0 en cas contrari
   */ 
  public int vertical(Tauler t, int columna, int fila, int color, int grup){
    int contador = 0;
    
    for(int i = fila; i < t.getMida(); i++){    
      if(t.getColor(i, columna) == t.getColor(fila, columna)){
        contador++;
      }
      else break;
    }
    
    if (contador >= grup){
      return 1;
    }
    else return 0;
  }

  /**
   * La comprovació horitzontal és igual que la vertical però en horitzontal, itera sobre cada columna.
   * @param t Tauler actual de joc
   * @param columna columna a explorar
   * @param fila fila a explorar
   * @param color Color de la peça que possarà
   * @param grup numero de fitxes del mateix jugador seguides
   * @return Retorna 1 si hi han mes fitxes que el numero de fitxes seguides actualmetn. Retorna 0 en cas contrari
   */
  public int horitzontal(Tauler t, int columna, int fila, int color, int grup){
    int contador = 0;
    
    for(int i = columna; i < t.getMida(); i++){
      if(t.getColor(fila, i) == t.getColor(fila, columna)){
        contador++;
      }
      else break;  
    }
    
    if (contador >= grup){
      return 1;
    }
    else return 0;
  }

  /**
   * Aquest cop, es comprovaran les diagonals, tant ascendents com descendents. Comencem amb un comptador per a mirar quantes fitxes hi ha seguides i una variable "total" per a saber si tenim només una diagonal o dos. Després, iterem en diagonal descendent (augmentant el valor de "i" i augmentant el valor de "j") i anem comptant les fitxes, si trobem una fitxa de diferent color parem. Si el nombre de fitxes trobades supera el grup aleshores tenim una diagonal. Seguidament, farem el mateix procediment però en diagonal ascendent (disminuint el valor de "i" i augmentant el valor de "j"). Finalment, mirem si el nombre de fitxes trobades és major o igual al grup i retornem "total".
   * @param t Tauler actual de joc
   * @param columna columna a explorar
   * @param fila fila a explorar
   * @param color Color de la peça que possarà
   * @param grup numero de fitxes del mateix jugador seguides
   * @return Retorna 1 si hi han mes fitxes que el numero de fitxes seguides actualmetn. Retorna 0 en cas contrari
   */
  public int diagonal(Tauler t, int columna, int fila, int color, int grup){
    int contador = 0;
    int total = 0;

    int j = columna;

    //Mirem la diagonal ascendent
    for (int i = fila; i < t.getMida(); i++)
    {
      if (j > t.getMida()-1)
      {
        break;
      }
      else if (t.getColor(fila, columna) == t.getColor(i, j))
      {
        contador += 1;
      }
      else break;

      j += 1;
    }
    if (contador >= grup) total += 1;

    //Mirem la diagonal descendent
    contador = 0;
    j = columna;

    for (int i = fila; i > -1; i--)
    {
      if (j > t.getMida()-1)
      {
        break;
      }
      else if (t.getColor(fila, columna) == t.getColor(i, j))
      {
        contador += 1;
      }
      else break;

      j += 1;
    }
    if (contador >= grup) total += 1;
    return total;
  }
  
  /**
   * Comencem assignant el color contrari a una variable "color_oponent" per a comptar bé les fitxes seguides. Aleshores, per cada posició del tauler comprovem el nombre de grups que hi ha des d'aquella casella, en vertical, horitzontal i diagonal, per cada color i grup. Després de mirar tots, tenim el nombre de 4 en ratlles sobre aquell tauler, el nombre de 3 en ratlles i el nombre de 2 en ratlla. Assignem un pes a cada grup perquè el minimax sàpiga quina jugada té més valor, nosaltres hem multiplicat els 4 en ratlla per 1000, els 3 en ratlla per 250 i els 2 en ratlla per 2. Finalment, restem les heurístiques de cada color (per a saber qui té una jugada més valuosa o guanyadora) i la retornem.
   * @param t Tauler actual de joc
   * @param color Color de la peça que possarà
   * @return retorna la heuristica (si es positiva, estarà al nostre favor, i si es negativa estara a favor de l'adversari)
   */
  public int heuristica(Tauler t, int color){
    // La variable maxima_puntuacio ens dira, en cas que no hi hagi ningun moviment guanyador, quin es el maxim de punts que podem aconseguir
    
    int color_oponent;
    if (color == 1)
    {
      color_oponent = -1;
    }
    else color_oponent = 1;

    int fitxes_meves_4 = 0;
    int fitxes_meves_3 = 0;
    int fitxes_meves_2 = 0; 
    int fitxes_oponent_4 = 0; 
    int fitxes_oponent_3  = 0;
    int fitxes_oponent_2 = 0; 

    for (int i = 0; i < t.getMida(); i++)
    {
      for (int j = 0; j < t.getMida(); j++)
      {
        if (t.getColor(i, j) == color)
        {
          //Mirem quants grups de 4 fitxes hi ha, en horitzontal, en vertical i en diagonal
          fitxes_meves_4 += horitzontal(t, i, j, color, 4);
          fitxes_meves_4 += vertical(t, i, j, color, 4);
          fitxes_meves_4 += diagonal(t, i, j, color, 4);

          //Mirem quants grups de 3 fitxes hi ha, en horitzontal, en vertical i en diagonal
          fitxes_meves_3 += horitzontal(t, i, j, color, 3);
          fitxes_meves_3 += vertical(t, i, j, color, 3);
          fitxes_meves_3 += diagonal(t, i, j, color, 3);
          
          //Mirem quants grups de 2 fitxes hi ha, en horitzontal, en vertical i en diagonal
          fitxes_meves_2 += horitzontal(t, i, j, color, 2);
          fitxes_meves_2 += vertical(t, i, j, color, 2);
          fitxes_meves_2 += diagonal(t, i, j, color, 2);
        }
        else if (t.getColor(i, j) == color_oponent)
        {
          //Mirem quants grups de 4 fitxes hi ha, en horitzontal, en vertical i en diagonal
          fitxes_oponent_4 += horitzontal(t, i, j, color_oponent, 4);
          fitxes_oponent_4 += vertical(t, i, j, color_oponent, 4);
          fitxes_oponent_4 += diagonal(t, i, j, color_oponent, 4);

          //Mirem quants grups de 3 fitxes hi ha, en horitzontal, en vertical i en diagonal
          fitxes_oponent_3 += horitzontal(t, i, j, color_oponent, 3);
          fitxes_oponent_3 += vertical(t, i, j, color_oponent, 3);
          fitxes_oponent_3 += diagonal(t, i, j, color_oponent, 3);

          //Mirem quants grups de 2 fitxes hi ha, en horitzontal, en vertical i en diagonal
          fitxes_oponent_2 += horitzontal(t, i, j, color_oponent, 2);
          fitxes_oponent_2 += vertical(t, i, j, color_oponent, 2);
          fitxes_oponent_2 += diagonal(t, i, j, color_oponent, 2);
        }
      }
    }

    // Retornem la puntuacio de la heuristica la qual es calcula com la suma de tots els grups de 4, 3 i 2 fitxes que tenim, restant els grups de 4, 3 i 2 fitxes que te l'oponent
    // Aixo es fa perque si tenim un grup de 4 fitxes i l'oponent no en te cap, aixo ens dona una puntuacio de 1000, i si l'oponent te un grup de 4 fitxes i nosaltres no en tenim cap, aixo ens dona una puntuacio de -1000
    // Es a dir, cada grup de fitxes, es multiplica per un pes que depen de la seva mida, i aixo ens dona una puntuacio que ens indica si tenim una posicio millor que l'oponent o no
    int fitxes_meves = fitxes_meves_4*1000 + fitxes_meves_3*250 + fitxes_meves_2*2;
    int fitxes_oponent = fitxes_oponent_4*1000 + fitxes_oponent_3*250 + fitxes_oponent_2*2;
    return fitxes_meves - fitxes_oponent;
  }

  
  // Creació de la funcio MINIMAX
  /**
   * 
   * @param t Tauler actual de joc
   * @param color Color de la peça que possarà
   * @param profunditat profunditat de minimax ( comptant torns de player 1 i player 2 separadament )
   * @param alpha 
   * @param beta
   * @param columna
   * @return 
   */
  public int[] minimax(Tauler t, int color, int profunditat, int alpha, int beta, int columna){
    // Si el joc s'ha acabat o la profunditat es 0, retornem la heuristica
      
    if (t.solucio(columna, -1))
    {
      return new int[]{0, 9999999};
    }
    else if (t.solucio(columna, 1))
    {
      return new int[]{0, -9999999};
    }
    else if (!t.espotmoure())
    {
      return new int[]{0,0};
    }
    else if (profunditat == 0)
    {
      return new int[]{0,heuristica(t, color)};
    }
    


    // Si es el torn de la maquina, busquem minimitzar
    if (color == -1){
      
      int[] max = new int[]{(int)(t.getMida() * Math.random()),-9999999};
      for (int i = 0; i < t.getMida(); i++){
        if (t.movpossible(i)){
          // Creem un nou tauler per a realitzar el moviment
          Tauler copia = new Tauler(t);
          copia.afegeix(i, color);

          // Recursivitat: cridem a la funcio amb el nou tauler, el color contrari i la profunditat disminuida, i actualitzem el valor de beta
          int[] proxim = minimax(copia, 1, profunditat - 1, alpha, beta, i);

          if(proxim[1] > max[1]){
            max[0] = i;
            max[1] = proxim[1];
          }
          alpha = Math.max(alpha, proxim[1]);
          // Si el valor de beta es menor o igual que alpha, retornem el valor de beta
          if (beta <= alpha) break;
        }
      }
      return max;
    }

    // Si es el nostre torn, busquem maximitzar
    else{
      int[] min = new int[]{(int)(t.getMida() * Math.random()),9999999};
      for (int i = 0; i < t.getMida(); i++){
        if (t.movpossible(i)){
          // Creem un nou tauler per a realitzar el moviment
          Tauler copia = new Tauler(t);  
          copia.afegeix(i, color);

          // Recursivitat: cridem a la funcio amb el nou tauler, el color contrari i la profunditat disminuida, i actualitzem el valor de beta
          int[] proxim = minimax(copia, -1, profunditat - 1, alpha, beta, i);
          
          if(proxim[1] < min[1]){
            min[0] = i;
            min[1] = proxim[1];
          }
          beta = Math.min(beta, proxim[1]);

          // Si el valor de beta es menor o igual que alpha, retornem el valor de beta
          if (beta <= alpha) break;
        }
      }
      return min;
    }
  }

  /**
   * Decideix el moviment del jugador donat un tauler i un color de peça que ha de posar.
   * @param t Tauler actual de joc
   * @param color Color de la peça que possarà
   * @return Columna on fer el moviment
   */
  public int moviment(Tauler t, int color)
  { 
    
    boolean buit = true;
    int i = 0;
    while(buit && i < t.getMida())
    {
      if (t.getColor(0, i) != 0 || t.getColor(0, i) == color)
      {
        buit = false;
      }
      ++i;
    }
    // Si el primer moviment el realitzem nosaltres (es a dir que som "p1"), realitzem la primera tirada a la columa 2
    // ja que despres de realitzar diverses proves, hem vist que es la columna més optima per a començar per al nostre minimax
    // normalment, un bon lloc per a realitzar la primera tirada tambe pot ser les caselles centrals, ja que si es dominen les caselles
    // centrals, hi han moltes possibilitats de guanyar la partida
    if (buit) {
      System.out.println("MOVIMENT TACTIC COLUMNA 2");
      return 1;
    }

    int[] a = minimax(t, color, profunditat, -9999999, 9999999, 0);

    System.out.println("MILLOR HEURISTICA: "+a[1]+" - COLUMNA: "+a[0]);
    return a[0];    
  }

  /**
   * Retorna el nom del jugador que s'utlilitza per visualització a la UI
   * @return Nom del jugador
   */
  public String nom()
  {
    return nom;
  }
}



