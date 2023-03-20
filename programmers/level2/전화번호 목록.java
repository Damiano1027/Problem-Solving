import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {        
        Arrays.sort(phone_book);
        
        for (int i = 0; i < phone_book.length - 1; i++) {
            String str1 = phone_book[i];
            for (int j = i + 1; j < phone_book.length; j++) {
                String str2 = phone_book[j];
                
                if (containsPrefix(str1, str2)) {
                    return false;
                }
                
                break;
            }
        }
        
        return true;
    }
    
    public boolean containsPrefix(String str1, String str2) {
        boolean result = false;
        
        if (str1.length() == str2.length()) {
            result = str1.equals(str2);
        }
        if (str1.length() < str2.length()) {
            result = str1.equals(str2.substring(0, str1.length()));
        }
        if (str2.length() < str1.length()) {
            result = str2.equals(str1.substring(0, str2.length()));
        }
        
        return result;
    }
}
