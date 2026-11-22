package ru.otus.danilchenko.atm.change;

import ru.otus.danilchenko.atm.cell.IGotCell;

import java.util.List;
import java.util.Objects;

public class ChangeModule {
    private final List<IGotCell> cellList;
    ChangeModule(List<IGotCell> cellList){
        Objects.requireNonNull(cellList);
        this.cellList = cellList;
    }

}
