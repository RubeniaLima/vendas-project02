package vendasproject02.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vendasproject02.domain.entity.Usuario;
import vendasproject02.domain.repository.UsuarioRepository;
import vendasproject02.exception.SenhaInvalidaException;

@Service
public class UsuarioServiceImpl implements UserDetailsService {
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl( @Lazy PasswordEncoder encoder) {
        this.encoder = encoder;
    }
    @Transactional
    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem= encoder.matches(usuario.getSenha(), user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw  new SenhaInvalidaException();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Usuario usuario = usuarioRepository.findByLogin(username)
              .orElseThrow(()->new UsernameNotFoundException("Usuário não encontrado na base de dados"));

      String[] roles =usuario.isAdmin()?
              new String[] {"ADMIN","USER"} : new String[]{"USER"};
      return User
              .builder().username(usuario.getLogin())
              .password(usuario.getSenha())
              .roles(roles)
              .build();
    }
}
