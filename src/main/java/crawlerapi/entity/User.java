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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Entity
@Table(name = "app_user")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class User implements UserDetails {

    @NonNull
    @Id
    @Column(nullable = false, length = 16, unique = true)
    private String username;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false, length = 80)
    private String password;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false, length = 64, unique = true)
    private String email;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private Boolean enabled;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    @CollectionTable(name = "app_user_roles", joinColumns = @JoinColumn(name = "username"))
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    private List<Role> roles;

    /** 更新回数 */
    @EqualsAndHashCode.Exclude
    @Version
    @XmlTransient
    private Long version;

    /** 登録ユーザ */
    @EqualsAndHashCode.Exclude
    @Column(name = "create_user")
    @XmlTransient
    private String createUser;

    /** 登録日時 */
    @EqualsAndHashCode.Exclude
    @Column(name = "create_date", updatable = false)
    @XmlTransient
    private LocalDateTime createDate;

    /** 更新ユーザ */
    @EqualsAndHashCode.Exclude
    @Column(name = "update_user")
    @XmlTransient
    private String updateUser;

    /** 更新日時 */
    @EqualsAndHashCode.Exclude
    @Column(name = "update_date")
    @XmlTransient
    private LocalDateTime updateDate;

    @Override
    public String getUsername() {
        return username;
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

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
