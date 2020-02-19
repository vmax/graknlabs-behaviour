/*
 * GRAKN.AI - THE KNOWLEDGE GRAPH
 * Copyright (C) 2019 Grakn Labs Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package grakn.verification.tools.operator;

import graql.lang.Graql;
import graql.lang.property.RelationProperty;
import graql.lang.statement.Statement;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    static RelationProperty relationProperty(Collection<RelationProperty.RolePlayer> relationPlayers) {
        if (relationPlayers.isEmpty()) return null;
        Statement var = Graql.var();
        List<RelationProperty.RolePlayer> sortedRPs = relationPlayers.stream()
                .sorted(Comparator.comparing(rp -> rp.getPlayer().var().symbol()))
                .collect(Collectors.toList());
        for (RelationProperty.RolePlayer rp : sortedRPs) {
            Statement rolePattern = rp.getRole().orElse(null);
            var = rolePattern != null ? var.rel(rolePattern, rp.getPlayer()) : var.rel(rp.getPlayer());
        }
        return var.getProperty(RelationProperty.class).orElse(null);
    }
}
