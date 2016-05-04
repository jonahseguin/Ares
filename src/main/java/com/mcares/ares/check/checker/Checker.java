package com.mcares.ares.check.checker;

import com.mcares.ares.check.CheckType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Checker extends CheckerBackend {

    private CheckType check;

    public Checker(CheckType check) {
        this.check = check;
    }

    public abstract CheckerSimilarity getSimilarityTo(Checker checker);

}
