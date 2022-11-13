/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.rel.convert;

import org.apache.calcite.plan.RelTraitDef;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A relational expression implements the interface <code>Converter</code> to
 * indicate that it converts a physical attribute, or
 * {@link org.apache.calcite.plan.RelTrait trait}, of a relational expression
 * from one value to another.
 *
 * <p>Sometimes this conversion is expensive; for example, to convert a
 * non-distinct to a distinct object stream, we have to clone every object in
 * the input.</p>
 *
 * <p>A converter does not change the logical expression being evaluated; after
 * conversion, the number of rows and the values of those rows will still be the
 * same. By declaring itself to be a converter, a relational expression is
 * telling the planner about this equivalence, and the planner groups
 * expressions which are logically equivalent but have different physical traits
 * into groups called <code>RelSet</code>s.
 *
 * <p>In principle one could devise converters which change multiple traits
 * simultaneously (say change the sort-order and the physical location of a
 * relational expression). In which case, the method {@link #getInputTraits()}
 * would return a {@link org.apache.calcite.plan.RelTraitSet}. But for
 * simplicity, this class only allows one trait to be converted at a
 * time; all other traits are assumed to be preserved.</p>
 *
 *
 * 用来把一种 RelTrait 转换为另一种 RelTrait 的 RelNode。如 JdbcToSparkConverter
 * 可以把 JDBC 里的 table 转换为 Spark RDD。如果需要在一个 RelNode 中处理来源于异构系统的逻辑表，
 * Calcite 要求先用 Converter 把异构系统的逻辑表转换为同一种 Convention。
 */
public interface Converter extends RelNode {
  //~ Methods ----------------------------------------------------------------

  /**
   * Returns the trait of the input relational expression.
   *
   * @return input trait
   */
  RelTraitSet getInputTraits();

  /**
   * Returns the definition of trait which this converter works on.
   *
   * <p>The input relational expression (matched by the rule) must possess
   * this trait and have the value given by {@link #getInputTraits()}, and the
   * traits of the output of this converter given by {@link #getTraitSet()} will
   * have one trait altered and the other orthogonal traits will be the same.
   *
   * @return trait which this converter modifies
   */
  @Nullable RelTraitDef getTraitDef();

  /**
   * Returns the sole input relational expression.
   *
   * @return child relational expression
   */
  RelNode getInput();
}
