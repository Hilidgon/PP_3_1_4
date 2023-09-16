package ru.kata.spring.boot_security.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;


@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public void edit(User newUser) {
        User user = entityManager.find(User.class, newUser.getId());
        if(newUser.getRoles().toString().equals("[]")) {
            newUser.setRoles(user.getRoles());
        }

        entityManager.merge(newUser);
    }

    @Override
    public User show(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String email) {
        Optional<User> user = entityManager
                .createQuery("from User user join fetch user.roles where user.email=:email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();

        return user.orElse(null);
    }
}
