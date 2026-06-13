package eu.gitonyx.gitonyx.factory;

public class RepositoryFactory {

    public <T> T loadModel(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public <T> void saveModel(T instance) {
        
    }
}
