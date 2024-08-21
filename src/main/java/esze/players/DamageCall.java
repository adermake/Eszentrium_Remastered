package esze.players;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DamageCall {

    private Entity damager;
    private int damage;
    private String spell;
    private boolean Void;
    private long millis;

}
