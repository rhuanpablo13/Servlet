package projeto.interfaces;

import java.util.List;

public interface DAO <T>{
	public boolean insert(T t)  throws Exception;
	public boolean delete(T t)  throws Exception;
	public boolean update(T t)  throws Exception;
	public List<T> selectList() throws Exception;
	public T searchOne(T t)     throws Exception;
	public T searchId(int t)      throws Exception;
}
