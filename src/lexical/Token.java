package lexical;

public class Token {

    private String token;
    private Type typeOfToken;
    private int id;

    public Token(String token, Type typeOfToken) {
        this.token = token;
        this.typeOfToken = typeOfToken;
        setId();
    }

    public Token(String token) {
        this.token = token;
        setTypeOfToken();
        setId();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Type getTypeOfToken() {
        return typeOfToken;
    }

    private void setTypeOfToken() {
        if (CONSTANT.KEYWORDS_LIST.indexOf(this.token) != -1) {
            this.typeOfToken = Type.KEYWORDS;

        } else if (CONSTANT.STANDARD_IDENTIFIERS_LIST.indexOf(this.token) != -1) {
            this.typeOfToken = Type.STANDARD_IDENTIFIER;
        } else {
            this.typeOfToken = Type.USER_IDENTIFIER;
        }
    }

    public int getId() {
        return id;
    }

    private void setId() {

        if (typeOfToken == Type.INTEGER) {
            this.id = CONSTANT.INTEGER_ID;

        } else if (typeOfToken == Type.FLOAT) {
            this.id = CONSTANT.FLOAT_ID;

        } else if (typeOfToken == Type.USER_IDENTIFIER) {
            this.id = CONSTANT.USER_IDENTIFIERS_ID;

        } else if(typeOfToken == Type.Error){
            this.id = -1;
        } else if (typeOfToken == Type.SYMBOL || typeOfToken == Type.KEYWORDS
                || typeOfToken == Type.STANDARD_IDENTIFIER) {
            this.id = getIdFromTable(typeOfToken);
        }
    }

    private int getIdFromTable(Type typeOfToken) {
        if (typeOfToken == Type.SYMBOL) {
            return CONSTANT.SYMBOLS_LIST.indexOf(this.token);

        } else if (typeOfToken == Type.KEYWORDS) {
            return CONSTANT.KEYWORDS_LIST.indexOf(this.token);

        } else if (typeOfToken == Type.STANDARD_IDENTIFIER) {
            return CONSTANT.STANDARD_IDENTIFIERS_LIST.indexOf(this.token);
        }
        return -1;
    }

    @Override
    public String toString() {
        return token + "\t " + typeOfToken + "\t " + id;
    }
}
