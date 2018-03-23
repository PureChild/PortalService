public class DaoFactory {
    public UserDao getUserDao() {
        return new UserDao(getConnectionMaker());
    }

    //반복되는 것은 Refactor + Extract + Method
    public ConnectionMaker getConnectionMaker() {
        return new JejuConnectionMaker();
    }
}
