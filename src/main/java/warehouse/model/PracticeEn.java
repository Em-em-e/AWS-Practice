package warehouse.model;

public class PracticeEn {
    private Integer id;

    private Integer no;

    private String str;

    private String ans;

    private String exp;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str == null ? null : str.trim();
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans == null ? null : ans.trim();
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp == null ? null : exp.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}