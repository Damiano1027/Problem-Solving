import java.util.*;

class Solution {
    Set<String> numberSet = new HashSet<>();
    
    public boolean solution(String[] phone_book) {
        for (String phone : phone_book) {
            numberSet.add(phone);
        }
        
        for (String phone : phone_book) {
            for (int i = 1; i < phone.length(); i++) {
                String frontStr = phone.substring(0, i);
                if (numberSet.contains(frontStr)) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
