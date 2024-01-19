package dao;

import entidades.Panel;
import entidades.Token;

public interface APIKeyDAOInterface {
    Token findById(Long id);
    Token createAPIKey(Token token);
    Token update(Token token);
    boolean deleteById(Long id);
}
