# Assignment 01 — TA feedback: Gagan (Gagana Kumar Konduri)

- **Evaluated by:** Yilin Pan (TA)
- **Date:** 2026-10-05

## Scores

| Category | Pts | Student's LLM eval | TA verified | Notes |
|---|---|---|---|---|
| Immutability & encapsulation | 25 | 24 | 25 | Non-final `Animal` came from the starter |
| Constructor validation | 25 | 24 | 25 | |
| Correctness | 15 | 15 | 15 | |
| Testing | 15 | 12 | 14 | Null-name message not tested |
| Code quality & style | 10 | 7 | 8 | 9 Checkstyle warnings |
| Introspection, LLM eval & code walk | 10 | 9 | 10 | `Species` is graded from the code walk |
| **Total** | **100** | **91** | **97** | |

## Checks

- [x] All tests pass (50/50)
- [x] No `UnsupportedOperationException` stubs left
- [ ] Checkstyle warnings: 9 (long line at AgeMonths.java:123, `if(` spacing at Animal.java:56–65)
- [x] Coverage (lines / branches): 100% / 100%
- [x] Nothing out of scope (inheritance, collections, `equals`/`hashCode`)
- [x] `submission/LLM-Evaluation.md` present
- [ ] `submission/introspection.md` present (saved as `introspection-template.md`)

## Where the TA disagrees with the student's LLM evaluation

- Immutability +1: `Animal` not being `final` came with the starter, so no deduction.
- Validation +1: the shared null/blank name message could be split in two, but it's not worth a point.
- Testing +2: re-tagging the provided tests as `inherited` didn't break anything, so I didn't take
  points for it. I kept −1 because the null-name message isn't tested.
- Code quality +1: import order in test files isn't checked. The Checkstyle warnings do count,
  though. How-to-submit asks you to clear them.
- Cat 6 +1: explaining `Species` is part of the code walk, not the introspection, and your code walk covered it, so full credit.

## Feedback

Your tests are good. They check message content, `MAX_MONTHS - 1`, and the em-space case that tells
`strip()` from `trim()`. The main thing to fix is Checkstyle: run `checkstyleMain` before you push.
All 9 warnings come from 5 lines. The nested ternary on AgeMonths.java:123 would also read better as
a small pluralize helper.

## Grade

**Final grade:** 97 / 100
