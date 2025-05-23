// This Java code is taken from a public GitHub repository
// and is used inside Aibolit only for integration testing
// purposes. The code is never compiled or executed.

// SPDX-FileCopyrightText: Copyright (c) 2019-2025 Aibolit
// SPDX-License-Identifier: MIT

/**
 * A compact array based segment tree implementation. This segment tree supports point updates and
 * range queries.
 *
 * @author Al.Cash & William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

public class CompactSegmentTree {
  private int N;

  // Let UNIQUE be a value which does NOT
  // and will not appear in the segment tree
  private long UNIQUE = 8123572096793136074L;

  // Segment tree values
  private long[] tree;

  public CompactSegmentTree(int size) {
    tree = new long[2 * (N = size)];
    java.util.Arrays.fill(tree, UNIQUE);
  }

  public CompactSegmentTree(long[] values) {
    this(values.length);
    for (int i = 0; i < N; i++) modify(i, values[i]);
  }

  // This is the segment tree function we are using for queries.
  // The function must be an associative function, meaning
  // the following property must hold: f(f(a,b),c) = f(a,f(b,c)).
  // Common associative functions used with segment trees
  // include: min, max, sum, product, GCD, and etc...
  private long function(long a, long b) {
    if (a == UNIQUE) return b;
    else if (b == UNIQUE) return a;

    // return a + b; // sum over a range
    // return (a > b) ? a : b; // maximum value over a range
    return (a < b) ? a : b; // minimum value over a range
    // return a * b; // product over a range (watch out for overflow!)
  }

  // Adjust point i by a value, O(log(n))
  public void modify(int i, long value) {
    tree[i + N] = function(tree[i + N], value);
    for (i += N; i > 1; i >>= 1) {
      tree[i >> 1] = function(tree[i], tree[i ^ 1]);
    }
  }

  // Query interval [l, r), O(log(n))
  public long query(int l, int r) {
    long res = UNIQUE;
    for (l += N, r += N; l < r; l >>= 1, r >>= 1) {
      if ((l & 1) != 0) res = function(res, tree[l++]);
      if ((r & 1) != 0) res = function(res, tree[--r]);
    }
    if (res == UNIQUE) {
      throw new IllegalStateException("UNIQUE should not be the return value.");
    }
    return res;
  }
}
