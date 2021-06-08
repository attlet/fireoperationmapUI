/*작성자 : 신윤성
* 내용 : 이미지 버튼이나 건물 정보 등을 저장하기 위한 리스트들 모음 */

package com.example.fireoperationmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OperationMap {
    public String name;
    public java.util.Map<Integer, java.util.Map<Integer, Place>> sectionData = new HashMap<>();
    public List<User> userList = new ArrayList<>();
    public List<Arcade> arcadeList = new ArrayList<>();
    public List<Approach> approachList = new ArrayList<>();
    public List<Auto_Arcade> auto_arcadeList = new ArrayList<>();
    public List<Fireplug> fireplugList = new ArrayList<>();
}
