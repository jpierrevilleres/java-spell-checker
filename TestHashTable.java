public class TestHashTable {
   public static void main(String[] args){
      HashTable table = new HashTable(2);  
      String key,value;
      while (true) {
         System.out.println("\nMenu:");
         System.out.println("   1. Try put(key,value)");
         System.out.println("   2. Try get(key)");
         System.out.println("   3. Try containsKey(key)");
         System.out.println("   4. Try remove(key)");
         System.out.println("   5. Show hash table contents.");
         System.out.println("   6. Exit");
         System.out.print("Enter your command:  ");
         switch ( TextIO.getlnInt()) {
         case 1:
            System.out.print("\n   Key = ");
            key = TextIO.getln();
            System.out.print("   Value = ");
            value = TextIO.getln();
            table.put(key,value);
            break;         
         case 2:
            System.out.print("\n   Key = ");
            key = TextIO.getln();
            System.out.println("   Value is " + table.get(key));
            break;         
         case 3:
            System.out.print("\n   Key = ");
            key = TextIO.getln();
            System.out.println("   containsKey(" + key + ") is " 
                  + table.containsKey(key));
            break;         
         case 4:
            System.out.print("\n   Key = ");
            key = TextIO.getln();
            table.remove(key);
            break;         
         case 5:
             table.dump();
             break;       
         default:
            System.out.println("   Illegal command.");
         break;
         }
         System.out.println("\nHash table size is " + table.size());
      }
   }

} // end class TestHashTable
