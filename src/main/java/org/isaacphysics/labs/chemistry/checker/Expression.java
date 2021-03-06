/**
 * Copyright 2016 James Sharkey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.isaacphysics.labs.chemistry.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Expression implements Countable {
    private ArrayList<AbstractTerm> terms;

    public Expression(AbstractTerm t) {
        terms = new ArrayList<AbstractTerm>();
        terms.add(t);
    }

    public Expression(Expression e, AbstractTerm t) {
        terms = new ArrayList<AbstractTerm>(e.terms);
        terms.add(t);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            if (i > 0) {
                b.append(" + ");
            }
            b.append(terms.get(i).toString());
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Expression) {
            Expression other = (Expression) o;
            HashSet<AbstractTerm> termSet = new HashSet<AbstractTerm>(terms);
            HashSet<AbstractTerm> otherTermSet = new HashSet<AbstractTerm>(other.terms);
            return termSet.equals(otherTermSet);
        }
        return false;
    }

    public HashMap<String, Integer> getAtomCount() {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        for (AbstractTerm t : terms) {
            for (String e : t.getAtomCount().keySet()) {
                if (!h.containsKey(e)) {
                    h.put(e,  t.getAtomCount().get(e));
                } else {
                    h.put(e, h.get(e) + t.getAtomCount().get(e));
                }
            }
        }
        return h;
    }

    public Integer getCharge() {
        Integer c = 0;
        for (AbstractTerm t : terms) {
            c += t.getCharge();
        }
        return c;
    }

    public boolean containsError() {
        for (AbstractTerm t : terms) {
            if (t instanceof ErrorTerm) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<AbstractTerm> getTerms() {
        return this.terms;
    }
}
