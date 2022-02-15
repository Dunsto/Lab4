import java.util.*;



public class HangmanManager{
   private Set<String> words; //from input words
   private ArrayList<String> wordList;
   private int length;        //length of words
   private int max;
   private SortedSet<Character> guessedChars;
   
   
   public HangmanManager(List<String> dictionary, int length, int max)
      throws IllegalArgumentException {
      
      if(length < 1 || max < 0){
         throw new IllegalArgumentException(" oh shi"); //fix up
      } else {
      
         this.length = length;               //sets length + max guesses
         this.max = max;
         
         words = new TreeSet<>(dictionary); //dict into set field / removes duplicates
         wordList = new ArrayList<>(words);
         removeWrongLength(words);          //removes wrong length words
         
         guessedChars = new TreeSet<>();    //Sets guessed chars field to empty tree/sorted Set
                    //test print
      }
   }
   
   
   public Set<String> words(){
   
      return words;
   }
   
   //not done
   public int guessLeft(){
      int guessesLeft = 1;
      return guessesLeft;
   }
   
   
   public SortedSet<Character> guesses(){
      
      return guessedChars;
   }
   
   public String pattern(){
      String pattern = "";

      for(int i = 0; i < length; i++){
         pattern = pattern + "-";
      }
      
      for(String s: words){
      for(int i = 0; i < s.length(); i++) {
         if(guessedChars.contains(s.charAt(i))){
            pattern = pattern.substring(0, i) + s.charAt(i) + pattern.substring(i + 1);
         }
      }
      }
      pattern = "this is the pattern" + pattern;
      return pattern;
   
   }
   
   
   //Returns larger set between words without multiple occurences of letter and words without
   //don't know when to call yet... getting messy in here lol
   private SortedSet<String> chooseMultiples(){
      SortedSet<String> multiples = new TreeSet<>();
      SortedSet<String> noMultiples = new TreeSet<>();
      
      for(String s: words){
         if(duplicateChars(s)){
            multiples.add(s);
         } else {
            noMultiples.add(s);
         }
      }
      
      if(multiples.size() > noMultiples.size()){
         return multiples;
      } else {
         return noMultiples;
      }
   }
   
   private boolean duplicateChars(String s){
      for(int i = 0; i < s.length() - 1; i++) {
         for(int j = i; j < s.length(); j++){
            if(s.charAt(i) == s.charAt(j)){
               return true;
            }
         }
      }
      return false;
   
   }
   
   
   
   public int record(char guess){
      sortSet(guess);
      
      guessedChars.add(guess);
      System.out.println(pattern());   
      System.out.println("You've guessed" + guessedChars);
      
      return 10;
   }
   
   
   private void sortSet(char guess){
      Map<Integer, Set<String>> sortedWords = new TreeMap<>();
      Set<String> wordsWithout = new TreeSet<>();
      
      
      //iterates through set of words adds words without guess to wordsWithout set
      //else if set contains index key already adds word to key
      //else creates new set of words with letters guessed at corresponding index
      //
      for(String current: words){
         if(current.indexOf(guess) < 0 ){
            wordsWithout.add(current);
                                     //increment wrong guess here?
         } else if(sortedWords.containsKey(current.indexOf(guess))) {         
            sortedWords.get(current.indexOf(guess)).add(current);
         } else {
            Set<String> set = new TreeSet<>();
            sortedWords.put(current.indexOf(guess), set);
            sortedWords.get(current.indexOf(guess)).add(current);
         }
      }
   
      //iterates through map of sortedWords
      //if wordsWithoutLetter > sortedWord index key/values
      //words = wordsWithoutLetter
      for(Integer index: sortedWords.keySet()){         
         if(wordsWithout.size() >= sortedWords.get(index).size()){
            words.clear();
            words.addAll(wordsWithout);
         } else if (sortedWords.get(index).size() > words.size()){
            words.clear();
            words.addAll(sortedWords.get(index));
         }
      }
      
      System.out.println("words without" + wordsWithout);
      System.out.println("sorted" + sortedWords);
      System.out.println("words set" + words);
   }
   
   
//    private void removeNoChar(char guess, Set<String> words){ //wrong shit
//       Iterator<String> itr = words.iterator();
//    
//       while(itr.hasNext()){
//          String current = itr.next();
//       
//          if(current.toLowerCase().indexOf(guess) < 0 ){
//          //   words.remove(current);
//                                      //increment wrong guess here?
//             itr.remove();
//          }
//       }
//       System.out.print("removeNoCharSuccess");
//    }
   
   
   private void removeWrongLength(Set<String> words){
      Iterator<String> itr = words.iterator();
      
      while(itr.hasNext()){
         String current = itr.next();
      
         if(current.length() != length){
            itr.remove();
         }
      }   
   }
}