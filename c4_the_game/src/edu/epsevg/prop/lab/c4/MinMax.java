package edu.epsevg.prop.lab.c4;

/**
 * MINIMAX
 * "Minimax mes heuristica"
 * @author eric
 */
public class MinMax implements Jugador, IAuto{
  private String nom;
  private int profunditat;

  public MinMax(int profunditat)
  {
    nom = "SUDO";
    this.profunditat = profunditat;
  }

  /*
  PRIMERA_HEURISTICA --> Per a la primerar heuristica es miraràn només les solucións en 4 en rallla que es puguin donar tant en vertical com en horitzontal. 
  Així, si la heuristica es menys infinit, vol dir que hi ha una possible solució (en vertical o horitzontal) per a l'altre jugador i si es mes infinit, vol 
  dir que hi ha una possible solució per a nosaltres (en vertical o horitzontal). També s'aniran guardant en una variable el numero maxim de fitxes en posició
  guanyadora que tenim i s'aniran sumant a la variable, per altra banda, s'aniran restant a la mateixa variable el numero maxim de fitxes en posició guanyadora 
  que tingui l'altre jugador. Així, si la heuristica es 0, vol dir que el tauler esta equilibrat, si es major que 0 vol dir que tenim mes fitxes que ell (en possible posició guanyadora)
  i si es menor que 0 vol dir que ell te mes fitxes (en possible posició guanyadora) que nosaltres.
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

  public String nom()
  {
    return nom;
  }
}



