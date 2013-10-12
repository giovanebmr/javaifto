/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package giovane;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Giovane
 */
public class UsuarioDao extends DBConnection {

    private final Connection conn;

    public UsuarioDao() throws ClassNotFoundException, SQLException {
        this.conn = this.getMyDBConnection();
    }
    
    /**
     * @param usuario 
     */
    public boolean AlterarUsuario(Usuario usuario) throws SQLException{
        String SQL = "UPDATE jogodavelha.jogador SET nome=?, login=?, email=?"
                +" WHERE id_jogador = ?";
        
        PreparedStatement stmt = conn.prepareStatement(SQL);
        
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getLogin());
        stmt.setString(3, usuario.getEmail());
        stmt.setInt(4, usuario.getId());
        
        return stmt.executeUpdate() > 0;
        
    }

    
    public void addUsuario(Usuario usuario) throws SQLException {
        String SQL = "insert into jogodavelha.jogador (nome, login, email, senha) values( ?,  ?,  ?,  ?)";

        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.execute();
        }
    }

    public List<Usuario> listaUsuarios() throws SQLException {
        String sql = "SELECT * FROM JOGODAVELHA.JOGADOR";
        PreparedStatement stmt;
        ResultSet rs;
        List usuarios = new LinkedList<Usuario>();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        while (rs.next()) {
            usuarios.add(getUsuario(rs));
        }
        return usuarios;
    }

    public Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id_jogador"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setLogin(rs.getString("login"));
        return usuario;
    }
}