# TheNews

## My Git Workflow

For Development:
1. Create `type/feature_name` from `dev`
2. Merge `type/feature_name` to `dev`
3. Delete `type/feature_name`(Optional)
4. When (features + fix) Done
   1. fully test the `dev` branch
   2. merge `dev` to `main`

For Release:
1. Create a annotated tag in `main` HEAD
2. Push `main` to remote
3. From that annotated tag create a release

## Branches

* Regular Git Branches
  * master: default branch available in the Git repository.
  * dev: main development branch

* Temporary Git Branches: branches that can be created and deleted when needed
  * Bug Fix
  * Hot Fix
  * Feature Branches
  * Experimental Branches
  * WIP branches (work is in progress)

## Naming Convention

* wip/<wip_name> : The work is in progress, and I am aware it will not finish soon
* bug/<bug_name> : The bug which needs to be fixed soon
* feat/<feature_name> : New Feature