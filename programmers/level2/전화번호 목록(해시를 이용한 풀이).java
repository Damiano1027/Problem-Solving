import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {        
        Set<String> set = new HashSet<>();
        
        for (String phone : phone_book) {
            if (phone.length() > 1) {
                for (int i = 1; i < phone.length(); i++) {
                    set.add(phone.substring(0, i));
                }
            }
        }
        
        for (String phone : phone_book) {
            if (set.contains(phone)) {
                return false;
            }
        }
        
        return true;
    }
}
