package crawlerapi.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import crawlerapi.security.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "app_user")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(of = { "username" })
public class User implements UserDetails {

    @Id
    @Column(nullable = false, length = 16, unique = true)
    private String username;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false)
    @Getter
    @Setter
    private Boolean enabled;

    @Column(nullable = false)
    @CollectionTable(name = "app_user_roles", joinColumns = @JoinColumn(name = "username"))
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private List<Role> roles;

    /** 更新回数 */
    @Version
    @XmlTransient
    private Long version;

    /** 登録ユーザ */
    @Column(name = "create_user")
    @XmlTransient
    private String createUser;

    /** 登録日時 */
    @Column(name = "create_date", updatable = false)
    @XmlTransient
    private LocalDateTime createDate;

    /** 更新ユーザ */
    @Column(name = "update_user")
    @XmlTransient
    private String updateUser;

    /** 更新日時 */
    @Column(name = "update_date")
    @XmlTransient
    private LocalDateTime updateDate;

    public User(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
