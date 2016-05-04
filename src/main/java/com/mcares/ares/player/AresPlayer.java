package com.mcares.ares.player;

import com.mcares.ares.cache.CachePlayer;
import com.mcares.ares.check.*;
import com.mcares.ares.check.checker.Checker;
import com.mcares.ares.database.mongo.annotations.CollectionName;
import com.mcares.ares.database.mongo.annotations.DatabaseSerializer;
import com.mcares.ares.database.mongo.annotations.MongoColumn;
import com.mcares.ares.database.mongo.serial.MapSerializer;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

@RequiredArgsConstructor
@AllArgsConstructor
@CollectionName(name = "aresplayers")
public class AresPlayer extends CachePlayer {

    public AresPlayer() {//So that AutoMongo can instantiate without throwing an InstantiationException
    }

    @Getter @Setter private Player bukkitPlayer = null;

    @MongoColumn(name = "username")
    @NonNull @Getter @Setter private String name;

    @MongoColumn(name = "uuid", identifier = true)
    @NonNull @Getter @Setter private String uniqueId;

    @MongoColumn(name = "vl")
    @DatabaseSerializer(serializer = MapSerializer.class)
    @Getter private final Map<CheckType, Integer> violations = new HashMap<>();
    @MongoColumn(name = "totalVL")
    @Getter @Setter private int totalVL = 0;

    @Getter @Setter private boolean alertsEnabled = true;

    @Getter private final Map<String, Violation> vls = new HashMap<>();

    @Getter private final CheckData data = new CheckData();

    private final Map<CheckType, Checker> checkers = new HashMap<>();

    public Violation addVL(CheckType checkType, boolean cancelled){
        Check check = CheckManager.get().getCheck(checkType);
        if (check == null) {
            throw new NullPointerException("Check can't be null");
        }
        totalVL += check.getRaiseLevel();
        if(!violations.containsKey(checkType)){
            violations.put(checkType,0);
        }
        violations.put(checkType,violations.get(checkType)+check.getRaiseLevel());

        final Violation violation = new Violation(this, this.getTotalVL(), checkType,
                this.getVL(checkType),cancelled);

        vls.put(violation.getId(), violation);

        return violation;
    }

    public int getVL(CheckType hackType){
        if(violations.containsKey(hackType)){
            return violations.get(hackType);
        }
        violations.put(hackType,0);
        return 0;
    }

    public Checker getChecker(CheckType checkType) {
        if(!checkers.containsKey(checkType)) {
            checkers.put(checkType, instantiateChecker(checkType));
        }
        return checkers.get(checkType);
    }

    private Checker instantiateChecker(CheckType checkType) {
        Checker checker;
        try{
            checker = checkType.getChecker().newInstance();
        }
        catch (IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException("Ares could not instantiate CheckData: ", ex);
        }
        return checker;
    }

}
