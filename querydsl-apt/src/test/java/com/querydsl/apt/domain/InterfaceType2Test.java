package com.querydsl.apt.domain;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;
import org.junit.Assert;
import org.junit.Test;

public class InterfaceType2Test {

    public interface IEntity {

        Long getId();
    }

    public interface User extends IEntity {

        Party getParty();

        String getUsername();

    }

    public interface Party extends IEntity {

        String getName();
    }


    @MappedSuperclass
    public static class EntityImpl {

        @Id
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

    }

    @Entity
    @Table(name = "USERS")
    @org.hibernate.annotations.AccessType("field")
    @org.hibernate.annotations.Proxy(proxyClass = User.class)
    public static class UserImpl extends EntityImpl implements User {

        @NaturalId(mutable = true)
        @Column(name = "USERNAME", nullable = false)
        private String username;

        @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, targetEntity = PartyImpl.class)
        @JoinColumn(name = "PARTY_ID", nullable = false)
        private Party party;

        @Override
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public Party getParty() {
            return party;
        }

        public void setParty(Party party) {
            this.party = party;
        }

    }

    @javax.persistence.Entity
    @Table(name = "PARTY")
    @org.hibernate.annotations.AccessType("field")
    @org.hibernate.annotations.Proxy(proxyClass = Party.class)
    public abstract static class PartyImpl extends EntityImpl implements Party {

        @Column(name = "NAME", nullable = false)
        private String name;

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    @Test
    public void test() {
        Assert.assertEquals(
                QInterfaceType2Test_PartyImpl.class,
                QInterfaceType2Test_UserImpl.Constants.userImpl.party.getClass());
    }

}
