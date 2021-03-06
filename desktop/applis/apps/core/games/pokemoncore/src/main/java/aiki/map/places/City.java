package aiki.map.places;

import aiki.db.DataBase;
import aiki.map.buildings.Building;
import aiki.map.buildings.Gym;
import aiki.map.buildings.PokemonCenter;
import aiki.map.enums.Direction;
import aiki.map.levels.Level;
import aiki.map.levels.LevelOutdoor;
import aiki.map.levels.Link;
import aiki.map.tree.LevelArea;
import aiki.map.tree.PlaceArea;
import aiki.map.tree.Tree;
import aiki.map.util.PlaceInterConnect;
import aiki.map.util.PlaceInterConnectCoords;
import aiki.map.util.PlaceInterConnects;
import aiki.util.Coords;
import aiki.util.Point;
import aiki.util.PointParam;
import aiki.util.Points;
import code.util.CustList;
import code.util.EntryCust;
import code.util.EqList;
import code.util.*;
import code.util.ObjectMap;
import code.util.core.IndexConstants;


public final class City extends Place implements InitializedPlace {

    /** key access to building, which is shown as a block */
    private Points< Building> buildings;

    private LevelOutdoor level;

    private String name;

    private PlaceInterConnects savedlinks;

    private PlaceInterConnects linksPointsWithCitiesAndOtherRoads = new PlaceInterConnects();

    private Points< Link> linksWithCaves;

    @Override
    public void addSavedLink(PlaceInterConnect _key, Coords _value) {
        savedlinks.put(_key, _value);
    }

    @Override
    public void validate(DataBase _data, PlaceArea _placeArea) {
        LevelArea levelArea_ = _placeArea.getLevel((byte) 0);
        boolean existPkCenter_ = false;
        int nbGyms_ = 0;
        EqList<Point> ids_ = new EqList<Point>();
        ids_.addAllElts(linksWithCaves.getKeys());
        ids_.addAllElts(buildings.getKeys());
        for (PointParam<Building> e : buildings.entryList()) {
            if (levelArea_.isAccessible(e.getKey())) {
                _data.setError(true);
            }
            if (!levelArea_.isValid(e.getKey(), false)) {
                _data.setError(true);
            }
            Point pt_ = new Point(e.getKey());
            pt_.moveTo(Direction.DOWN);
            if (!levelArea_.isValid(pt_, true)) {
                _data.setError(true);
            }
            ids_.add(pt_);
            Building building_ = e.getValue();
            building_.validate(_data,
                    _placeArea.getBuildings().getVal(e.getKey()));
            if (building_ instanceof PokemonCenter) {
                existPkCenter_ = true;
            }
            if (building_ instanceof Gym) {
                nbGyms_++;
            }
        }
        if (!existPkCenter_ || nbGyms_ > 1) {
            _data.setError(true);
        }
        for (PlaceInterConnect p : linksPointsWithCitiesAndOtherRoads.getKeys()) {
            if (!levelArea_.isValid(p.getSource(), false)) {
                _data.setError(true);
            }
        }
        if (ids_.hasDuplicates()) {
            _data.setError(true);
        }
        for (Point p : linksWithCaves.getKeys()) {
            if (!levelArea_.isValid(p, false)) {
                _data.setError(true);
            }
            Coords c_ = linksWithCaves.getVal(p).getCoords();
            if (!_data.getMap().existCoords(c_)) {
                _data.setError(true);
                continue;
            }
            Place tar_ = _data.getMap().getPlace(c_.getNumberPlace());
            Level tarLevel_ = tar_.getLevelByCoords(c_);
            if (!tarLevel_.isEmptyForAdding(c_.getLevel().getPoint())) {
                _data.setError(true);
            }
        }
        getLevelOutdoor().validate(_data, levelArea_);
    }

    @Override
    public boolean hasValidImage(DataBase _data) {
        boolean val_ = true;
        if (!super.hasValidImage(_data)) {
            val_ = false;
        }
        for (Building b : buildings.values()) {
            if (!b.hasValidImage(_data)) {
                val_ = false;
            }
        }
        return val_;
    }

    @Override
    public boolean isEmptyForAdding(Coords _coords) {
        Level level_ = getLevelByCoords(_coords);
        return level_.isEmptyForAdding(_coords.getLevel().getPoint());
    }

    @Override
    public boolean validLinks(short _place, Tree _tree) {
        for (PlaceInterConnectCoords e : linksPointsWithCitiesAndOtherRoads
                .entryList()) {
            if (!_tree.isValid(e.getCoords(), false)) {
                return false;
            }
        }
        for (PointParam<Link> e : linksWithCaves.entryList()) {
            Link link_ = e.getValue();
            if (!_tree.isValid(link_.getCoords(), true)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ByteMap< Level> getLevelsMap() {
        ByteMap< Level> levels_ = new ByteMap< Level>();
        levels_.put(IndexConstants.FIRST_INDEX, getLevelOutdoor());
        return levels_;
    }

    @Override
    public CustList<Level> getLevelsList() {
        return new CustList<Level>(getLevelOutdoor());
    }

    @Override
    public Level getLevelByCoords(Coords _coords) {
        if (_coords.isInside()) {
            Point bIncome_ = _coords.getInsideBuilding();
            return buildings.getVal(bIncome_).getLevel();
        }
        return getLevelOutdoor();
    }

    public Points< Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(Points< Building> _buildings) {
        buildings = _buildings;
    }

    @Override
    public Level getLevel() {
        return getLevelOutdoor();
    }

    public LevelOutdoor getLevelOutdoor() {
        return level;
    }

    public void setLevel(LevelOutdoor _level) {
        level = _level;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String _name) {
        name = _name;
    }

    @Override
    public PlaceInterConnects getSavedlinks() {
        return savedlinks;
    }

    @Override
    public void setSavedlinks(PlaceInterConnects _savedlinks) {
        savedlinks = _savedlinks;
    }

    @Override
    public PlaceInterConnects getPointsWithCitiesAndOtherRoads() {
        return linksPointsWithCitiesAndOtherRoads;
    }

    @Override
    public void setPointsWithCitiesAndOtherRoads(
            PlaceInterConnects _linksPointsWithCitiesAndOtherRoads) {
        linksPointsWithCitiesAndOtherRoads = _linksPointsWithCitiesAndOtherRoads;
    }

    @Override
    public Points< Link> getLinksWithCaves() {
        return linksWithCaves;
    }

    public void setLinksWithCaves(Points< Link> _linksWithCaves) {
        linksWithCaves = _linksWithCaves;
    }

}
