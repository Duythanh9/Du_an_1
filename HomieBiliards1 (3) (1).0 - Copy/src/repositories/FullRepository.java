/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import java.util.List;

/**
 *
 * @author Mtt
 */
public interface FullRepository<T> {

    public List<T> selectAll();

    public List<T> selectDeleted();

    public boolean create(T entity);

    public boolean update(T entity, int id);

    public boolean delete(int id);

}
