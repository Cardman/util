package code.util.graphs;
import code.util.CustList;
import code.util.EqList;
import code.util.core.IndexConstants;
import code.util.ints.GraphElement;

public final class Graph<T extends GraphElement<T>> {

    private final CustList<ArrowedSegment<T>> segments = new CustList<ArrowedSegment<T>>();
    private final EqList<T> elements = new EqList<T>();

//    private final List<T> froms = new List<T>();

//    private final List<T> tos = new List<T>();

//    private final List<T> separations = new List<T>();

    public void addSegment(T _from, T _to) {
        addSegment(new ArrowedSegment<T>(_from, _to));
    }
    public void addReversedSegment(ArrowedSegment<T> _seg) {
        addSegment(new ArrowedSegment<T>(_seg.getTo(), _seg.getFrom()));
    }

    public void addSegment(ArrowedSegment<T> _seg) {
        segments.add(_seg);
        boolean addFrom_ = true;
        boolean addTo_ = true;
        T e_ = _seg.getFrom();
        for (T e: elements) {
            if (e.eq(e_)) {
                addFrom_ = false;
                break;
            }
        }
        if (addFrom_) {
//            froms.add(e_);
            elements.add(e_);
        }
        e_ = _seg.getTo();
        for (T e: elements) {
            if (e.eq(e_)) {
                addTo_ = false;
                break;
            }
        }
        if (addTo_) {
//            tos.add(e_);
            elements.add(e_);
        }
        /*if (!addFrom_ && !addTo_) {
            boolean sep_ = false;
            for (T e: separations) {
                if (e.eq(e_)) {
                    sep_ = true;
                    break;
                }
            }
            if (!sep_) {
                separations.add(e_);
            }
        }*/
    }

    public boolean isTrees() {
        if (isDirectTrees()) {
            return true;
        }
        return getReverse().isDirectTrees();
    }
    public EqList<T> getTreeFrom(T _elt) {
        EqList<T> current_ = new EqList<T>();
        current_.add(_elt);
        EqList<T> visited_ = new EqList<T>();
        visited_.add(_elt);
        EqList<T> new_ = new EqList<T>();
        while (true) {
            for (T t: current_) {
                for (ArrowedSegment<T> u: getChildrenSegments(t)) {
                    addVisited(visited_, new_, u);
                }
            }
            if (new_.isEmpty()) {
                break;
            }
            current_ = new_;
            new_ = new EqList<T>();
        }
        return visited_;
    }

    private boolean match(EqList<T> _visited, ArrowedSegment<T> _u) {
        return visited(_visited, _u.getTo());
    }

    public EqList<T> elementsCycle() {
//        if (segments.isEmpty()) {
//            return new List<T>();
//        }
        EqList<T> sep_ = getDynamicSeparations();
//        if (!hasPseudoRoots()) {
//            if (sep_.isEmpty()) {
//                return getElements();
//            }
//        }
        EqList<T> l_ = new EqList<T>();
//        if (!hasPseudoRoots()) {
//            int i_ = List.FIRST_INDEX;
//            if (sep_.isEmpty()) {
//                return getElements();
//            }
//            while (true) {
//                T s_ = sep_.get(i_);
//                List<Segment<T>> lines_ = new List<Segment<T>>();
//                List<T> current_ = new List<T>(s_);
//                List<T> visited_ = new List<T>(s_);
//                List<T> new_ = new List<T>();
//                boolean found_ = false;
//                while (true) {
//                    for (T t: current_) {
//                        for (Segment<T> u: getChildrenSegments(t)) {
//                            lines_.add(u);
//                            if (u.getTo().eq(s_)) {
//                                found_ = true;
//                                Graph<T> gLoc_ = new Graph<T>();
//                                for (Segment<T> l: lines_) {
//                                    gLoc_.addReversedSegment(l);
//                                }
//                                for (Segment<T> m: gLoc_.getLines(s_)) {
//                                    T to_ = m.getTo();
//                                    boolean add_ = true;
//                                    for (T e: l_) {
//                                        if (e.eq(to_)) {
//                                            add_ = false;
//                                            break;
//                                        }
//                                    }
//                                    if (!add_) {
//                                        continue;
//                                    }
//                                    l_.add(to_);
//                                }
//                                break;
//                            }
//                            boolean ex_ = false;
//                            for (T e: visited_) {
//                                if (e.eq(u.getTo())) {
//                                    //!e.eq(s_)
//                                    ex_ = true;
//                                    break;
//                                }
//                            }
//                            if (ex_) {
//                                continue;
//                            }
//                            new_.add(u.getTo());
//                            visited_.add(u.getTo());
//                        }
//                        if (found_) {
//                            break;
//                        }
//                    }
//                    if (found_) {
//                        break;
//                    }
//                    if (new_.isEmpty()) {
//                        break;
//                    }
//                    current_ = new_;
//                    new_ = new List<T>();
//                }
//                if (found_) {
//                    break;
//                }
//                i_++;
//            }
//            return l_;
//        }
        for (T s: sep_) {
            if (found(s,l_)) {
                break;
            }
        }
        return l_;
    }
    private boolean found(T _s, EqList<T> _l) {
        EqList<T> current_ = new EqList<T>();
        current_.add(_s);
        EqList<T> visited_ = new EqList<T>();
        visited_.add(_s);
        boolean found_ = false;
        CustList<ArrowedSegment<T>> lines_ = new CustList<ArrowedSegment<T>>();
        while (true) {
            EqList<T> new_ = new EqList<T>();
            for (T t: current_) {
                for (ArrowedSegment<T> u: getChildrenSegments(t)) {
                    lines_.add(u);
                    if (u.getTo().eq(_s)) {
                        addSegs(_l, _s, lines_);
                        found_ = true;
                        break;
                    }
                    addVisited(visited_, new_, u);
                }
                if (found_) {
                    break;
                }
            }
            if (stop(new_, found_)) {
                break;
            }
            current_ = new_;
        }
        return found_;
    }

    private boolean stop(EqList<T> _new, boolean _found) {
        return _found || _new.isEmpty();
    }

    private void addVisited(EqList<T> _visited, EqList<T> _new, ArrowedSegment<T> _u) {
        boolean ex_ = match(_visited, _u);
        if (ex_) {
            return;
        }
        _new.add(_u.getTo());
        _visited.add(_u.getTo());
    }

    private void addSegs(EqList<T> _l, T _s, CustList<ArrowedSegment<T>> _lines) {
        Graph<T> gLoc_ = new Graph<T>();
        for (ArrowedSegment<T> l: _lines) {
            gLoc_.addReversedSegment(l);
        }
        for (ArrowedSegment<T> m: gLoc_.getLines(_s)) {
            T to_ = m.getTo();
            boolean add_ = add(_l, to_);
            if (!add_) {
                continue;
            }
            _l.add(to_);
        }
    }

    private boolean add(EqList<T> _l, T _to) {
        boolean add_ = true;
        for (T e: _l) {
            if (e.eq(_to)) {
                add_ = false;
                break;
            }
        }
        return add_;
    }

    public CustList<ArrowedSegment<T>> getLines(T _root) {
        EqList<T> current_ = new EqList<T>();
        current_.add(_root);
        EqList<T> visited_ = new EqList<T>();
        visited_.add(_root);
        EqList<T> new_ = new EqList<T>();
        CustList<ArrowedSegment<T>> lines_ = new CustList<ArrowedSegment<T>>();
        while (true) {
            for (T t: current_) {
                addNewVisit(visited_, new_, lines_, t);
            }
            if (new_.isEmpty()) {
                break;
            }
            current_ = new_;
            new_ = new EqList<T>();
        }
        return lines_;
    }

    private void addNewVisit(EqList<T> _visited, EqList<T> _new, CustList<ArrowedSegment<T>> _lines, T _t) {
        for (ArrowedSegment<T> u: getChildrenSegments(_t)) {
            _lines.add(u);
            addVisited(_visited, _new, u);
        }
    }

    //    public boolean quickHasCycle() {
//        if (segments.isEmpty()) {
//            return false;
//        }
//        if (!hasPseudoRoots()) {
//            return true;
//        }
//        for (T s: getSeparations()) {
//            List<T> current_ = new List<T>(s);
//            List<T> visited_ = new List<T>(s);
//            List<T> new_ = new List<T>();
//            while (true) {
//                for (T t: current_) {
//                    for (T u: getChildren(t)) {
//                        if (u.eq(s)) {
//                            return true;
//                        }
//                        boolean ex_ = false;
//                        for (T e: visited_) {
//                            if (e.eq(u)) {
//                                //!e.eq(s)
//                                ex_ = true;
//                                break;
//                            }
//                        }
//                        if (ex_) {
//                            continue;
//                        }
//                        new_.add(u);
//                        visited_.add(u);
//                    }
//                }
//                if (new_.isEmpty()) {
//                    break;
//                }
//                current_ = new_;
//                new_ = new List<T>();
//            }
//        }
//        return false;
//    }
//    public boolean quickHasCycle(T _t) {
//        List<Segment<T>> mystack_ = new List<Segment<T>>();
//        mystack_.add(new Segment<T>(_t, null));
//        List<Pair<T,Boolean>> visited_ = new List<Pair<T,Boolean>>();
//        for (T e: getElements()) {
//            visited_.add(new Pair<T,Boolean>(e, false));
//        }
//        for (Pair<T,Boolean> p: visited_) {
//            if (p.getFirst().eq(_t)) {
//                p.setSecond(true);
//                break;
//            }
//        }
////        visited[start] = true;
//
//        while (!mystack_.isEmpty())
//        {
//            T f_ = mystack_.last().getFrom();
//            T t_ = mystack_.last().getTo();
//            mystack_.removeLast();
//
////            const auto &edges = mygraph->edges[v];
//            List<T> edges_ = getChildren(f_);
//            for (T i: edges_) {
////                boolean v_ = false;
//                Pair<T,Boolean> p_ = null;
//                for (Pair<T,Boolean> p: visited_) {
//                    if (p.getFirst().eq(i)) {
//                        p_ = p;
////                        v_ = p.getSecond();
//                        break;
//                    }
//                }
//                if (!p_.getSecond()) {
//                    mystack_.add(new Segment<T>(i, f_));
////                    for (Pair<T,Boolean> p: visited_) {
////                        if (p.getFirst().eq(i)) {
////                            p.setSecond(true);
////                        }
////                    }
//                    p_.setSecond(true);
//                    continue;
////                    visited[w] = true;
//                }
//                if (!i.eq(t_)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
    public boolean hasCycle() {
        if (segments.isEmpty()) {
            return false;
        }
        if (!hasPseudoRoots()) {
            return true;
        }
        return hasCycleDef();
    }

    private boolean hasCycleDef() {
        for (T s: getDynamicSeparations()) {
            if (hasCycleOne(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCycleOne(T _s) {
        EqList<T> current_ = new EqList<T>();
        current_.add(_s);
        EqList<T> visited_ = new EqList<T>();
        visited_.add(_s);
        while (true) {
            EqList<T> new_ = new EqList<T>();
            for (T t: current_) {
                for (T u: getChildren(t)) {
                    if (u.eq(_s)) {
                        return true;
                    }
                    tryAddVisited(visited_, new_, u);
                }
            }
            if (new_.isEmpty()) {
                break;
            }
            current_ = new_;
        }
        return false;
    }
    private void tryAddVisited(EqList<T> _visited, EqList<T> _new, T _u) {
        boolean ex_ = visited(_visited, _u);
        if (ex_) {
            return;
        }
        _new.add(_u);
        _visited.add(_u);
    }

    private boolean visited(EqList<T> _visited, T _u) {
        boolean ex_ = false;
        for (T e: _visited) {
            if (e.eq(_u)) {
                //!e.eq(s)
                ex_ = true;
                break;
            }
        }
        return ex_;
    }

    public EqList<T> getChildren(T _element) {
        EqList<T> c_ = new EqList<T>();
        for (ArrowedSegment<T> s: segments) {
            if (s.getFrom().eq(_element)) {
                c_.add(s.getTo());
            }
        }
        return c_;
    }
    public CustList<ArrowedSegment<T>> getChildrenSegments(T _element) {
        CustList<ArrowedSegment<T>> c_ = new CustList<ArrowedSegment<T>>();
        for (ArrowedSegment<T> s: segments) {
            if (s.getFrom().eq(_element)) {
                c_.add(s);
            }
        }
        return c_;
    }
//    public List<T> getSeparations() {
//        return separations;
////        List<T> r_ = new List<T>();
////        List<T> elts_ = getElements();
////        for (T e: elts_) {
////            int nbOne_ = List.SIZE_EMPTY;
////            for (Segment<T> s: segments) {
////                if (s.getTo().eq(e)) {
////                    nbOne_++;
////                }
////            }
////            int nbTwo_ = List.SIZE_EMPTY;
////            for (Segment<T> s: segments) {
////                if (s.getFrom().eq(e)) {
////                    nbTwo_++;
////                }
////            }
//////            if (nb_ >= List.ONE_ELEMENT) {
//////                r_.add(e);
//////                continue;
//////            }
//////            if (nbTwo_ < List.ONE_ELEMENT) {
//////                continue;
//////            }
//////            if (nbOne_ <= List.ONE_ELEMENT) {
//////                continue;
//////            }
////            if (nbOne_ <= List.ONE_ELEMENT) {
////                if (nbTwo_ <= List.ONE_ELEMENT) {
////                    continue;
////                }
////            }
////            r_.add(e);
//////            if (nbOne_ == List.ONE_ELEMENT) {
//////                if (nbTwo_ > List.ONE_ELEMENT) {
//////                    r_.add(e);
//////                }
//////            }
//////            if (nbOne_ > List.ONE_ELEMENT) {
//////                if (nbTwo_ >= List.ONE_ELEMENT) {
//////                    r_.add(e);
//////                }
//////            }
//////            if (nbOne_ < List.ONE_ELEMENT) {
//////                continue;
//////            }
////////////            if (nbTwo_ > List.ONE_ELEMENT) {
//////                r_.add(e);
//////            }
////        }
////        return r_;
//    }
    public EqList<T> getDynamicSeparations() {
        EqList<T> r_ = new EqList<T>();
        EqList<T> elts_ = getElements();
        for (T e: elts_) {
            int nbOne_ = IndexConstants.SIZE_EMPTY;
            for (ArrowedSegment<T> s: segments) {
                if (s.getTo().eq(e)) {
                    nbOne_++;
                }
            }
            if (nbOne_ == IndexConstants.SIZE_EMPTY) {
                continue;
            }
            int nbTwo_ = IndexConstants.SIZE_EMPTY;
            for (ArrowedSegment<T> s: segments) {
                if (s.getFrom().eq(e)) {
                    nbTwo_++;
                }
            }
            if (nbTwo_ == IndexConstants.SIZE_EMPTY) {
                continue;
            }
//            if (nbOne_ <= List.ONE_ELEMENT) {
//                if (nbTwo_ <= List.ONE_ELEMENT) {
//                    continue;
//                }
//            }
            r_.add(e);
        }
        return r_;
    }
    public boolean hasPseudoRoots() {
        EqList<T> elts_ = getElements();
        for (T e: elts_) {
            int nb_ = IndexConstants.SIZE_EMPTY;
            for (ArrowedSegment<T> s: segments) {
                if (s.getTo().eq(e)) {
                    nb_++;
                }
            }
            if (nb_ == IndexConstants.SIZE_EMPTY) {
                return true;
            }
            nb_ = IndexConstants.SIZE_EMPTY;
            for (ArrowedSegment<T> s: segments) {
                if (s.getFrom().eq(e)) {
                    nb_++;
                }
            }
            if (nb_ == IndexConstants.SIZE_EMPTY) {
                return true;
            }
        }
        return false;
    }
    public EqList<T> pseudoRoots() {
        EqList<T> r_ = new EqList<T>();
        EqList<T> elts_ = getElements();
        for (T e: elts_) {
            int nb_ = IndexConstants.SIZE_EMPTY;
            for (ArrowedSegment<T> s: segments) {
                if (s.getTo().eq(e)) {
                    nb_++;
                }
            }
            if (nb_ == IndexConstants.SIZE_EMPTY) {
                r_.add(e);
                continue;
            }
            nb_ = IndexConstants.SIZE_EMPTY;
            for (ArrowedSegment<T> s: segments) {
                if (s.getFrom().eq(e)) {
                    nb_++;
                }
            }
            if (nb_ == IndexConstants.SIZE_EMPTY) {
                r_.add(e);
            }
        }
        return r_;
    }
    public boolean isDirectTrees() {
        if (segments.isEmpty()) {
            return true;
        }
        if (!hasPseudoRoots()) {
            return false;
        }
        EqList<T> elts_ = getElements();
//        boolean exist_ = false;
        for (T e: elts_) {
            int nb_ = IndexConstants.SIZE_EMPTY;
            for (ArrowedSegment<T> s: segments) {
                if (s.getTo().eq(e)) {
                    nb_++;
                }
            }
//            if (nb_ == List.SIZE_EMPTY) {
//                exist_ = true;
//            }
//            if (nb_ == List.ONE_ELEMENT) {
//                continue;
//            }
//            if (nb_ > List.ONE_ELEMENT || !exist_)
            if (nb_ > IndexConstants.ONE_ELEMENT) {
                return false;
            }
        }
        return true;
    }

    public Graph<T> getReverse() {
        Graph<T> r_ = new Graph<T>();
        for (ArrowedSegment<T> s: segments) {
            r_.addReversedSegment(s);
        }
        return r_;
    }

    public EqList<T> getElementsListCopy() {
        return new EqList<T>(elements);
    }

    EqList<T> getElements() {
        return elements;
//        List<T> l_ = new List<T>();
//        for (Segment<T> s: segments) {
//            boolean addFrom_ = true;
//            boolean addTo_ = true;
//            for (T e: l_) {
//                if (e.eq(s.getFrom())) {
//                    addFrom_ = false;
//                }
//                if (e.eq(s.getTo())) {
//                    addTo_ = false;
//                }
//            }
//            if (addFrom_) {
//                l_.add(s.getFrom());
//            }
//            if (addTo_) {
//                l_.add(s.getTo());
//            }
//        }
//        return l_;
    }
}
