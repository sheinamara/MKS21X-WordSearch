//////////////////////
// TESTING TERMINAL //
//////////////////////
// 3 Arguments:
//    YES; gives directions when we don't have all arguments or have an invalid fileName
// 4 Arguments:
// 5 Arguments:
import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception
public class WordSearch{
    private char[][]data;

    //the random seed used to produce this WordSearch
    private int seed;

    //a random Object to unify your random calls
    private Random randgen;

    //all words from a text file get added to wordsToAdd, indicating that they have not yet been added
    private ArrayList<String>wordsToAdd;

    //all words that were successfully added get moved into wordsAdded.
    private ArrayList<String>wordsAdded;

    public static void main(String[]args){
      String directions = "\nTo make your wonderful WordSearch, you need to give me (the terminal) three things!\nPlease enter: java WordSearch <row> <col> <fileName>.\nIf there is a specific seed you want, you can enter it in right after the three necessary parameters!\nIt should look like: java WordSearch <row> <col> <fileName> <seed>.\nCompletely sick and tired of your puzzle and want to know the answers?\nEnter: java WordSearch <row> <col> <fileName> <seed> <key>.\nIf your seed isn't working, remember it must be between 0 and 10000 inclusive.\nAlso, please make sure your input for rows or columns are whole numbers!";
      if (args.length < 3){
        System.out.println(directions);
      }
      if (args.length == 3){
        try{
          int roaringRows = Integer.parseInt(args[0]);
          int coolCols = Integer.parseInt(args[1]);
          String fabulousFile = args[2];
          int seed = (int)(Math.abs(Math.random()*10000));
          WordSearch theUltimateWordSearch = new WordSearch(roaringRows, coolCols, fabulousFile, seed);
          System.out.println(theUltimateWordSearch);
        }
        catch(FileNotFoundException f){
          System.out.println("Does your file exist? Please check your file name and try again." + directions);
        }
        catch(NumberFormatException e){
          System.out.println("Did you enter in an integer? Or is it something else?" + directions);
        }
        catch(IndexOutOfBoundsException n){
          System.out.println("Check your index!" + directions);
        }
        catch(NegativeArraySizeException a){
          System.out.println("We can't have negative rows or columns!" + directions);
        }
      }
      if (args.length == 4){
        try{
          int roaringRows = Integer.parseInt(args[0]);
          int coolCols = Integer.parseInt(args[1]);
          String fabulousFile = args[2];
          int seed = Integer.parseInt(args[3]);
          System.out.println(seed);
          if (seed > 10000 || seed < 0){
            System.out.println(directions);
          }
          WordSearch theUltimateWordSearch = new WordSearch(roaringRows, coolCols, fabulousFile, seed);
          System.out.println(theUltimateWordSearch);
        }
        catch(FileNotFoundException f){
          System.out.println("Does your file exist? Please check your file name and try again." + directions);
        }
        catch(NumberFormatException e){
          System.out.println("Did you enter in an integer? Or is it something else?" + directions);
        }
        catch(IndexOutOfBoundsException n){
          System.out.println("Check your index!" + directions);
        }
        catch(NegativeArraySizeException a){
          System.out.println("We can't have negative rows or columns!" + directions);
        }
      }
      if (args.length == 5){
        try{
          int roaringRows = Integer.parseInt(args[0]);
          int coolCols = Integer.parseInt(args[1]);
          String fabulousFile = args[2];
          int seed = Integer.parseInt(args[3]);
          if (seed > 10000 || seed < 0){
            System.out.println(directions);
          }
          WordSearch theUltimateWordSearch = new WordSearch(roaringRows, coolCols, fabulousFile, seed);
          theUltimateWordSearch.answers();
          System.out.println(theUltimateWordSearch);
        }
        catch(FileNotFoundException f){
          System.out.println("Does your file exist? Please check your file name and try again." + directions);
        }
        catch(NumberFormatException e){
          System.out.println("Did you enter in an integer? Or is it something else?" + directions);
        }
        catch(IndexOutOfBoundsException n){
          System.out.println("Check your index!" + directions);
        }
        catch(NegativeArraySizeException a){
          System.out.println("We can't have negative rows or columns!" + directions);
        }
      }
    }

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    public WordSearch(int rows,int cols){
      data = new char[rows][cols];
      clear();
    }

    public WordSearch(int rows, int cols, String fileName, int randSeed) throws FileNotFoundException{
      String directions = "\nTo make your wonderful WordSearch, you need to give me (the terminal) three things!\nPlease enter: java WordSearch <row> <col> <fileName>.\nIf there is a specific seed you want, you can enter it in right after the three necessary parameters!\nIt should look like: java WordSearch <row> <col> <fileName> <seed>.\nCompletely sick and tired of your puzzle and want to know the answers?\nEnter: java WordSearch <row> <col> <fileName> <seed> <key>.\nIf your seed isn't working, remember it must be between 0 and 10000 inclusive.\nAlso, please make sure your input for rows or columns are whole numbers!";
      data = new char[rows][cols];
      clear();
      seed = randSeed;
      wordsAdded = new ArrayList<String>();
      wordsToAdd = new ArrayList<String>();
      randgen = new Random(seed);
      File toRead = new File(fileName);
      Scanner search = new Scanner(toRead);
      while (search.hasNext()){ // returns true if this scanner has another token in its input
        wordsToAdd.add(search.next()); // finds and returns the next complete token from this scanner
      }
      addAllWords();
      fillIn();
    }

    /**Set all values in the WordSearch to underscores'_'*/
    private void clear(){
      for (int i = 0; i < data.length; i++){
        for (int x = 0; x < data[i].length; x++){
          data[i][x] = '_';
        }
      }
    }

    /**Fills the grid with random letters*/
    private void fillIn(){
      String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      for (int i = 0; i < data.length; i++){
        for (int x = 0; x < data[i].length; x++){
          if (data[i][x] == '_'){
            int replacement = randgen.nextInt() % 26; // 26 letters in the alphabet!!!
            if (replacement < 0){
              replacement = replacement * -1;
            }
            char randomLetter = alpha.charAt(replacement);
            data[i][x] = randomLetter;
          }
        }
      }
    }

    /**Removes the random letters which ultimately reveals the answers*/
    public void answers(){
      for (int i = 0; i < data.length; i++){
        for (int x = 0; x < data[x].length; x++){
          if (data[i][x] == '_'){
            data[i][x] = ' ';
          }
        }
      }
    }

    /**Each row is a new line, there is a space between each letter
     *@return a String with each character separated by spaces, and rows
     *separated by newlines.
     */
    public String toString(){
      // the actual grid
      String puzzle = "";
      for (int i = 0; i < data.length; i++){
        puzzle = puzzle + "| ";
        for (int x = 0; x < data[i].length; x++){
          puzzle = puzzle + data[i][x] + " ";
        }
        puzzle = puzzle + " |\n";
      }
      // the list of words in the puzzle
      String whatToSearch = "";
      for (int x = 0; x < wordsAdded.size(); x++){
        if (x != wordsAdded.size() - 1){
          whatToSearch = whatToSearch + wordsAdded.get(x) + ", ";
        }
        else{
          whatToSearch = whatToSearch + wordsAdded.get(x);
        }
      }
      // adding it all together to print
      puzzle = puzzle + "Words: " + whatToSearch + "\nSeed: " + seed;
      return puzzle;
    }


    /**Attempts to add a given word to the specified position of the WordGrid.
    *The word is added in the direction rowIncrement,colIncrement
    *Words must have a corresponding letter to match any letters that it overlaps.
    *
    *@param word is any text to be added to the word grid.
    *@param row is the vertical locaiton of where you want the word to start.
    *@param col is the horizontal location of where you want the word to start.
    *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
    *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction
    *@return true when: the word is added successfully.
    *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
    *        OR there are overlapping letters that do not match
    */
   private boolean addWord(String word, int row, int col, int rowIncrement, int colIncrement){
     word = word.toUpperCase();
     for (int i = 0; i < word.length(); i++){
       if (row < 0 || col < 0){
         return false;
       }
       if (rowIncrement == 0 && colIncrement == 0){
         return false;
       }
       // have to check if it fits
       if (row + i * rowIncrement >= data.length || col + i * colIncrement >= data.length){
         return false;
       }
       if (row + i * rowIncrement < 0 || col + i * colIncrement < 0){
         return false;
       }
       if (data[row + i * rowIncrement][col + i * colIncrement] != '_' && data[row + i * rowIncrement][col + i * colIncrement] != word.charAt(i)){
         return false;
       }
     }
     for (int x = 0; x < word.length(); x++){
       data[row + x * rowIncrement][col + x * colIncrement] = word.charAt(x);
     }
     return true;
   }
   /*[rowIncrement,colIncrement] examples:
    *[-1,1] would add up and the right because (row -1 each time, col + 1 each time)
    *[ 1,0] would add downwards because (row+1), with no col change
    *[ 0,-1] would add towards the left because (col - 1), with no row change
    */

    private void addAllWords(){
      // we need tries
      int tries = 0;
      for (int i = 0; i < wordsToAdd.size(); i++){
        String target = wordsToAdd.get(i);
        int startRowHere = randgen.nextInt() % data.length;
        int startColHere = randgen.nextInt() % data[0].length;
        int incRowHere = randgen.nextInt(3) - 1;
        int incColHere = randgen.nextInt(3) - 1;
        tries = 0;
        if (addWord(target, startRowHere, startColHere, incRowHere, incColHere) == false){
          while (tries < 5 && addWord(target, startRowHere, startColHere, incRowHere, incColHere) == false){
            tries = tries + 1;
            if (addWord(target, startRowHere, startColHere, incRowHere, incColHere)){
              wordsAdded.add(target);
              wordsToAdd.remove(i);
            }
          }
          if (wordsAdded.contains(target) == true){
            wordsAdded.add(target);
            wordsToAdd.remove(i);
          }
          else{
            wordsToAdd.remove(i);
          }
        }
        if (addWord(target, startRowHere, startColHere, incRowHere, incColHere)){
          wordsAdded.add(target);
          wordsToAdd.remove(i);
        }
      }
    }

////////////////////////////////////
//  WE DO NOT NEED THESE METHODS  //
////////////////////////////////////
    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from left to right, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     * or there are overlapping letters that do not match, then false is returned
     * and the board is NOT modified.
     */
/*
    public boolean addWordHorizontal(String word,int row, int col){
      // testing if the word can be added
      if (row > data.length || col >= data[row].length || row < 0 || col <0) {
        return false;
      }
      if (word.length() > data[row].length - col) {
        return false;
      }
      for (int i = 0; i < word.length(); i++){
        if ((data[row][col + i] != '_') && (data[row][col + i] != word.charAt(i))){
          return false;
        }
      }
      // the actual adding of the word
      for (int x = 0; x < word.length(); x++){
        data[row][col + x] = word.charAt(x);
      }
      return true;
    }
*/

   /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top to bottom, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     *and the board is NOT modified.
     */
/*
    public boolean addWordVertical(String word,int row, int col){
      // testing if the word can be added
      if (row > data.length || col >= data[row].length || row < 0 || col <0) {
        return false;
      }
      if (word.length() > data.length - row) {
        return false;
      }
      for (int i = 0; i < word.length(); i++){
        if ((data[row + i][col] != '_') && (data[row + i][col] != word.charAt(i))){
          return false;
        }
      }
      // the actual adding of the word
      for (int x = 0; x < word.length(); x++){
        data[row + x][col] = word.charAt(x);
      }
      return true;
    }
*/

    /**Attempts to add a given word to the specified position of the WordGrid.
    *The word is added from top left to bottom right, must fit on the WordGrid,
    *and must have a corresponding letter to match any letters that it overlaps.
    *
    *@param word is any text to be added to the word grid.
    *@param row is the vertical locaiton of where you want the word to start.
    *@param col is the horizontal location of where you want the word to start.
    *@return true when the word is added successfully. When the word doesn't fit,
    *or there are overlapping letters that do not match, then false is returned.
    */
/*
   public boolean addWordDiagonal(String word,int row, int col){
     if (word.length() > data.length-row || word.length() > data[row].length - col || row < 0 || col < 0) {
       return false;
     }
     for (int i = 0; i < word.length(); i++){
       if (data[row + i][col + i] != '_' && data[row + i][col + i] != word.charAt(i)){
         return false;
       }
     }
     for (int i = 0; i < word.length(); i++){
       data[row + i][col + i] = word.charAt(i);
     }
     return true;
   }
*/

}
