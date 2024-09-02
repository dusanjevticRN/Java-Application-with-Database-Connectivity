package person.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Korisnik
{
    private String ime;
    private String prezime;
    private String username;
    private String password;

    public Korisnik(String ime, String prezime, String username, String password)
    {
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.password = password;
    }
}
