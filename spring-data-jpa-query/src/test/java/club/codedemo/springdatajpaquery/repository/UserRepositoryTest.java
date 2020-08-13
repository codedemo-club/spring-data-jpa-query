package club.codedemo.springdatajpaquery.repository;

import club.codedemo.springdatajpaquery.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    /**
     * 2.1 JPQL 查询
     * 方法作用：获取所有激活用户
     */
    @Test
    @Transactional
    void findAllActiveUsersUsingJPQL() {
        Collection<User> users = this.userRepository.findAllActiveUsersUsingJPQL();
        Assertions.assertEquals(users.size(), 2);
    }

    /**
     * 2.2 Native 原生查询
     * 方法作用： 获取所有激活用户
     */
    @Test
    @Transactional
    void findAllActiveUsersUsingNative() {
        Collection<User> users = this.userRepository.findAllActiveUsersUsingNative();
        Assertions.assertEquals(users.size(), 2);
    }

    /**
     * 3.1 使用JPA内置方法 进行Sort()排序操作
     * 方法作用： 按对象的"名称"属性音序排序
     * 注：new Sort()无法再实例化，因此改为Sort.by()
     */
    @Test
    @Transactional
    void findAllUsersSortByObjectPropertiesNameUsingJpaMethods() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhangsan");
        Assertions.assertEquals(users.get(4).getName(), "zhaoliu");
    }

    /**
     * 3.1 使用JPA内置方法 进行Sort()排序操作
     * 方法作用： 按字符串"Name"的长度排序（非对象属性）
     * 注：new Sort()无法再实例化，因此改为Sort.by()
     */
    @Test
    @Transactional
    void findAllSortByPropertyNameUsingJpaMethods() {

        List<User> users = userRepository.findAll(Sort.by("LENGTH(name)"));
        // 测试不会通过，会出现异常，但我们仍断言结果，Name长度依次递增
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhaoliu");
        Assertions.assertEquals(users.get(4).getName(), "zhangsan");
    }

    /**
     * 3.2 JPQL 进行Sort()排序操作
     * 方法作用： 按字符串"名称"的音序排序(非对象属性)
     */
    @Test
    @Transactional
    void findAllUsersSortByObjectPropertiesNameUsingJPQL() {
        List<User> users = userRepository.findAllUsersSortUsingJPQL(Sort.by("name"));
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhangsan");
        Assertions.assertEquals(users.get(4).getName(), "zhaoliu");
    }

    /**
     * 3.2 JPQL 进行Sort()排序操作
     * 方法作用： 按字符串"名称"的长度排序(非对象属性)(使用Unsafe)
     */
    @Test
    @Transactional
    void findAllUsersSortByPropertyNameUnsafeUsingJPQL() {
        List<User> users = userRepository.findAllUsersSortUsingJPQL(JpaSort.unsafe("LENGTH(name)"));
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhaoliu");
        Assertions.assertEquals(users.get(4).getName(), "zhangsan");
    }

    /**
     * 3.2 JPQL 进行Sort()排序操作
     * 方法作用： 按字符串"名称"的长度排序(非对象属性)(不使用Unsafe)
     */
    @Test
    @Transactional
    void findAllUsersSortByPropertyNameUsingJPQL() {
        List<User> users = userRepository.findAllUsersSortUsingJPQL(Sort.by("LENGTH(name)"));
        // 测试不会通过，会出现异常，但我们仍断言结果，Name长度依次递增
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhaoliu");
        Assertions.assertEquals(users.get(4).getName(), "zhangsan");
    }

    /**
     * 3.3 Native原生查询 进行Sort()排序操作
     * 方法作用： 按对象的"Name"属性音序排序
     */
    @Test
    @Transactional
    void findAllUsersSortByObjectPropertiesNameUsingNative() {
        List<User> users = userRepository.findAllUsersSortUsingNative(Sort.by("name"));
        // 测试不会通过，会出现异常，但我们仍断言结果，Name按音序依次递增
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhangsan");
        Assertions.assertEquals(users.get(4).getName(), "zhaoliu");
    }

    /**
     * 4.1 JPQL 分页
     * 方法作用：获取全部用户，并按传入的Pageable进行分页
     */
    @Test
    @Transactional
    void findAllUsersWithPaginationUsingJPQL() {
        Pageable page = PageRequest.of(1, 2);
        Page<User> users = userRepository.findAllUsersWithPaginationUsingJPQL(page);
        Assertions.assertEquals(users.getSize(), 2);
        Assertions.assertEquals(users.getContent().get(0).getName(), "zhangsan");
        Assertions.assertEquals(users.getContent().get(1).getName(), "lisi");
    }
}