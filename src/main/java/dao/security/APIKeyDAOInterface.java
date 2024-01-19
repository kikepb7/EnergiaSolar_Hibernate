package dao.security;

import entidades.Token;

import java.util.List;

public interface APIKeyDAOInterface {
    Token findById(Long id);
    List<Token> getAllTokens();
    Token createAPIKey(Token token);
    Token update(Token token);
    boolean deleteById(Long id);
    boolean validateAPIKey(String apiKey);
}
