package by.runa.lib.api.exceptions;

import by.runa.lib.entities.AEntity;

public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 5024315615518026922L;

    private Class<? extends AEntity> clazz;

    public EntityNotFoundException(Class<? extends AEntity> clazz) {
        super("Sorry, no " + clazz + " with this id");
        this.clazz = clazz;
    }
}
