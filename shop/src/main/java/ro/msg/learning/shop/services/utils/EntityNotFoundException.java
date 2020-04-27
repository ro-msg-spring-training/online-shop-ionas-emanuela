package ro.msg.learning.shop.services.utils;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(int id, String entity) {
        super("could not find " + entity + " with id " + id);
    }

    public EntityNotFoundException(String name, String entity) {
        super("could not find " + entity + " named " + name);
    }

}
