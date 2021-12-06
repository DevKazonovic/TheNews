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
