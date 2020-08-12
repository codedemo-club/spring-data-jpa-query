package club.codedemo.springdatajpaquery.repository;

import club.codedemo.springdatajpaquery.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaSpecificationExecutor, JpaRepository<User, Long> {

    /**
     * 2.1 JPQL 查询
     * 方法作用：获取所有激活用户
     * @return 所有激活用户的集合
     */
    @Query("SELECT u FROM User u WHERE u.status = 1")
    Collection<User> findAllActiveUsersUsingJPQL();

    /**
     * 2.2 Native 原生查询
     * 方法作用： 获取所有激活用户
     * @return 所有激活用户的集合
     */
    @Query(value = "SELECT * FROM USERS u WHERE u.status = 1", nativeQuery = true)
    Collection<User> findAllActiveUsersUsingNative();

    /**
     * 3.2 JPQL Sort排序
     * 方法作用：获取全部用户，并按传入的Sort进行排序
     * @return 所有用户的List
     */
    @Query(value = "SELECT u FROM User u")
    List<User> findAllUsersSortUsingJPQL(Sort sort);
}
