package com.sbp.copyrightStreet.boundedContext.member;



import com.sbp.copyrightStreet.boundedContext.article.borad.Board;

import com.sbp.copyrightStreet.boundedContext.article.recomment.Recomment;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder // Member.builder().providerTypeCode(providerTypeCode) .. 이런식으로 쓸 수 있게 해주는
@NoArgsConstructor // @Builder 붙이면 이거 필수
@AllArgsConstructor // @Builder 붙이면 이거 필수
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate 작동하게 허용
@ToString // 디버그를 위한
@Entity // 아래 클래스는 member 테이블과 대응되고, 아래 클래스의 객체는 테이블의 row와 대응된다.
@Getter // 아래 필드에 대해서 전부다 게터를 만든다. private Long id; => public Long getId() { ... }
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @CreatedDate // 아래 칼럼에는 값이 자동으로 들어간다.(INSERT 할 때)
    private LocalDateTime createDate;
    @LastModifiedDate // 아래 칼럼에는 값이 자동으로 들어간다.(UPDATE 할 때 마다)
    private LocalDateTime modifyDate;
    private String providerTypeCode; // 일반회원인지, 카카오로 가입한 회원인지, 구글로 가입한 회원인지
    @Column(unique = true)
    private String username;
    private String password;
    @Column
    private String email;
    private String phoneNumber;

    @Column(unique = true)
    private String loginId;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Board> boardList;
    @OneToOne // 1:1

    // 이 함수 자체는 만들어야 한다. 스프링 시큐리티 규격
    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // 모든 멤버는 member 권한을 가진다.
        grantedAuthorities.add(new SimpleGrantedAuthority("member"));

        // username이 admin인 회원은 추가로 admin 권한도 가진다.
        if ("admin".equals(username)) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }

        return grantedAuthorities;
    }
}