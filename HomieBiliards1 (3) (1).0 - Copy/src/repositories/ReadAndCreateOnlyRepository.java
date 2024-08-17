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
public interface ReadAndCreateOnlyRepository<T> {

    public List<T> selectAll();
    public boolean create(T entity);
}
